package com.thinkjoy.ucenter.jms;

import com.alibaba.fastjson.JSON;
import com.thinkjoy.common.util.AESUtil;
import com.thinkjoy.common.util.UUIDTool;
import com.thinkjoy.ucenter.dao.mapper.UcenterDatasyncLogMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterSchoolMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserIdentityMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserMapper;
import com.thinkjoy.ucenter.dao.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * MQ消费者
 * Created by xufei
 */
public class DefaultMessageQueueListener implements MessageListener {

    private static Logger _log = LoggerFactory.getLogger(DefaultMessageQueueListener.class);

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    UcenterUserMapper ucenterUserMapper;
    @Autowired
    UcenterUserIdentityMapper ucenterUserIdentityMapper;
    @Autowired
    UcenterSchoolMapper ucenterSchoolMapper;
    @Autowired
    UcenterDatasyncLogMapper ucenterDatasyncLogMapper;

    public static final String USERTYPE_STUDENT="1";
    public static final String USERTYPE_TEACHER="3";
    public static final String TABLENAME_USER="用户基本信息表";

    @Override
    public void onMessage(final Message message) {
        // 毫秒转日期
        final Calendar c = Calendar.getInstance();

        // 使用线程池多线程处理
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                TextMessage textMessage = (TextMessage) message;
                UcenterDatasyncLog ucenterDatasyncLog=new UcenterDatasyncLog();
                StringBuffer stringBuffer=new StringBuffer();
                String text =null;
                UcenterUser ucenterUser=null;
                List<UcenterSchool> ucenterSchools=null;
                String flag="0";//执行结果
                try {
                    text = textMessage.getText();
                    ucenterUser= JSON.parseObject(text,UcenterUser.class);
                    /**
                     * 以 学校编码和身份证号,手机号为依据
                     * 只同步账号基本信息：考生号，姓名，身份证号，电话，性别字段，只新增不修改(尽量不修改用户中心原有数据，只做增量)
                     * 1.判断学校编码是否为空，如果为空记录日志；如果不为空，判断用户中心中是否存在该学校，存在继续下一步，不存在则记录日志
                     *   判断用户类型  1-学生，3-老师
                     * 学生处理
                     * 老师处理
                     *
                     * 约定  描述字段存学生类型 1-学生，3-老师
                     * 约定  创建ip字段存数据来源
                     * 这样做的原因：1、避免创建复杂的数据结构，2、可以通过创建人、描述、创建ip存储的数据标识同步的数据，方便数据出错后排查
                     */
                    if(ucenterUser.getSchoolCode()!=null){
                        UcenterSchoolExample ucenterSchoolExample=new UcenterSchoolExample();
                        ucenterSchoolExample.createCriteria().andSchoolCodeEqualTo(ucenterUser.getSchoolCode());
                        ucenterSchools=ucenterSchoolMapper.selectByExample(ucenterSchoolExample);
                        if(ucenterSchools!=null||ucenterSchools.size()>0) {
                            if(ucenterSchools.size()==1){
                                if (USERTYPE_STUDENT.equals(ucenterUser.getDescription())) {//学生处理
                                    flag=studentinfoAsyn(ucenterUser,ucenterSchools.get(0),stringBuffer);
                                }else if(USERTYPE_TEACHER.equals(ucenterUser.getDescription())){//老师处理
                                    flag=userinfoAsyn(ucenterUser,ucenterSchools.get(0),stringBuffer);
                                }else{
                                    stringBuffer.append(" 未处理用户身份:"+ucenterUser.getDescription());
                                    flag = "0";
                                }
                            }else{
                                stringBuffer.append(" 用户中心存在重复编码学校");
                            }
                        } else {
                            stringBuffer.append(" 学校在用户中心不存在");
                        }
                    }else{
                        stringBuffer.append(" 导入数据学校编码为空");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    stringBuffer.append(e.toString());
                }finally {
                    String message=stringBuffer.toString();
                    _log.info("消费：{}", text + ":" +message );
                    c.setTimeInMillis(ucenterUser.getCtime());
                    Date date = c.getTime();
                    if(message.endsWith("|")){
                        message=message.substring(0,message.length()-1);
                    }
                    ucenterDatasyncLog.setDatasyncSystem(ucenterUser.getCreateIp());
                    ucenterDatasyncLog.setData(text);
                    ucenterDatasyncLog.setDatasyncTime(date);
                    ucenterDatasyncLog.setSchoolCode(ucenterUser.getSchoolCode());
                    ucenterDatasyncLog.setCreator(ucenterUser.getCreator());
                    ucenterDatasyncLog.setDataLog(message);
                    ucenterDatasyncLog.setStatus(flag);
                    ucenterDatasyncLog.setTableName(TABLENAME_USER);
                    if(ucenterSchools!=null&&ucenterSchools.size()==1){
                        ucenterDatasyncLog.setSchoolName(ucenterSchools.get(0).getSchoolName());
                    }
                    ucenterDatasyncLogMapper.insertSelective(ucenterDatasyncLog);
                }
            }
        });
    }

    /**
     * 学生数据处理   基本信息已用户中心为主，用户中心数据可以覆盖智慧迎新数据
     * @param ucenterUser
     * @param stringBuffer
     * @return
     * 1.如果用户不存在，新增用户，用户身份
     * 2.通过身份证号判断 用户 是否存在，如果存在记录日志，然后判断电话号码是否相同，相同说明是同一个用户，不相同说明数据有误，记录日志
     *    判断考生号是否为空，为空补填考生号字段，下一步
     *    判断已存在的账户考生号和新导入的考生号是否相同，相同继续，不相同记录日志
     *    判断账户对应的用户身份信息是否存在，如果存在记录日志，如果不存在则新增
     *    在身份证号，电话，考生号相同的情况下，查询是否有身份信息，没有新增
     *
     */
    public String studentinfoAsyn(UcenterUser ucenterUser,UcenterSchool ucenterSchool,StringBuffer stringBuffer){
        String flag="0";
        String examineeNumber=null;
        //验证重复，身份证号,手机号       默认：身份证号,手机号都不能为空
        UcenterUserExample ucenterUserExample = new UcenterUserExample();
        ucenterUserExample.or().andIdcardEqualTo(ucenterUser.getIdcard());
        ucenterUserExample.or().andUsernameEqualTo(ucenterUser.getPhone());
        List<UcenterUser> ucenterUsers = ucenterUserMapper.selectByExample(ucenterUserExample);

        if (ucenterUsers==null||ucenterUsers.size() == 0) {
            ucenterUser.setUid(UUIDTool.getUUID());
            ucenterUser.setUsername(ucenterUser.getPhone());
            ucenterUser.setPassword(AESUtil.AESEncode(ucenterUser.getIdcard().substring(
                    ucenterUser.getIdcard().length() - 6, ucenterUser.getIdcard().length())));
            int count = ucenterUserMapper.insertSelective(ucenterUser);

            //保存用户身份
            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
            ucenterUserIdentity.setUserId(ucenterUser.getUserId());
            ucenterUserIdentity.setUsertypeId(Integer.parseInt(ucenterUser.getDescription()));
            //身份类型
            ucenterUserIdentity.setRelationCode(ucenterUser.getSchoolCode());
            ucenterUserIdentity.setRelationName(ucenterSchool.getSchoolName());
            ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);

            stringBuffer.append(" 新增用户、用户身份 |");
            flag = "1";
        } else {
            if (ucenterUsers!=null&&ucenterUsers.size()==1) {
                if(ucenterUsers.get(0).getUsername().equals(ucenterUser.getPhone())&&ucenterUsers.get(0).getIdcard().equals(ucenterUser.getIdcard())){
                    stringBuffer.append(" 身份证号已经存在(用户已经存在) |");
                    UcenterUserExample ucenterUserExample2 = new UcenterUserExample();
                    ucenterUserExample2.createCriteria().andIdcardEqualTo(ucenterUser.getIdcard()).andPhoneEqualTo(ucenterUser.getPhone());

                    if (ucenterUsers.get(0).getExamineeNumber() == null || "".equals(ucenterUsers.get(0).getExamineeNumber())) {
                        UcenterUser ucenterUserOne = new UcenterUser();
                        ucenterUserOne.setExamineeNumber(ucenterUser.getExamineeNumber());
                        ucenterUserMapper.updateByExampleSelective(ucenterUserOne, ucenterUserExample2);

                        stringBuffer.append(" 更新考生号 |");
                        examineeNumber = ucenterUser.getExamineeNumber();
                    } else {
                        examineeNumber = ucenterUsers.get(0).getExamineeNumber();
                    }

                    if (ucenterUser.getExamineeNumber().equals(examineeNumber)) {
                        UcenterUserIdentityExample ucenterUserIdentityExample = new UcenterUserIdentityExample();
                        ucenterUserIdentityExample.createCriteria().andUserIdEqualTo(ucenterUsers.get(0).getUserId())
                                .andUsertypeIdEqualTo(Integer.parseInt(ucenterUser.getDescription())).andRelationCodeEqualTo(ucenterUser.getSchoolCode());
                        long totalUserIdentity = ucenterUserIdentityMapper.countByExample(ucenterUserIdentityExample);
                        if (totalUserIdentity == 0) {
                            //保存用户身份
                            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
                            ucenterUserIdentity.setUserId(ucenterUsers.get(0).getUserId());
                            ucenterUserIdentity.setUsertypeId(Integer.parseInt(ucenterUser.getDescription()));
                            //身份类型
                            ucenterUserIdentity.setRelationCode(ucenterUser.getSchoolCode());
                            ucenterUserIdentity.setRelationName(ucenterSchool.getSchoolName());
                            ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);

                            stringBuffer.append(" 新增用户身份 |");
                        } else {
                            stringBuffer.append(" 用户身份已经存在 |");
                        }

                        flag = "1";
                    } else {
                        stringBuffer.append(" 身份证号相同，考生号不同 |");
                        flag = "0";
                    }
                }else{
                    if(ucenterUsers.get(0).getIdcard().equals(ucenterUser.getIdcard())){
                        stringBuffer.append(" 身份证号相同，电话号码不同 |");
                    }
                    if(ucenterUsers.get(0).getUsername().equals(ucenterUser.getPhone())){
                        stringBuffer.append(" 电话号码相同，身份证号不同 |");
                    }
                    flag = "0";
                }

            }else {
                stringBuffer.append(" 系统有身份证号相同或用户名（手机号）相同的账户 |");
                flag = "0";
            }
        }
        return flag;
    }

    /**
     * 老师数据处理
     * @param ucenterUser
     * @param stringBuffer
     * @return
     * 1、老师约定使用手机号作为登录号字段，使用用户名去用户表的电话字段查询
     * 2、如果查询结果为空，新增一个用户信息，用户名，手机号字段都填手机号
     * 3、如果手机号对应有用户，记录日志（老师用手机号作为登录名，可能出现重复，所有暂且不做修改操作）
     * 4、判断对应用户是否有老师身份，如果没有，新增
     */
    public String userinfoAsyn(UcenterUser ucenterUser,UcenterSchool ucenterSchool,StringBuffer stringBuffer){
        String flag="0";
        UcenterUserExample ucenterUserExample = new UcenterUserExample();
        ucenterUserExample.createCriteria().andUsernameEqualTo(ucenterUser.getPhone());
        List<UcenterUser> ucenterUsers = ucenterUserMapper.selectByExample(ucenterUserExample);
        if (ucenterUsers==null||ucenterUsers.size() == 0) {
            ucenterUser.setUid(UUIDTool.getUUID());
            ucenterUser.setUsername(ucenterUser.getPhone());
            ucenterUser.setPassword(AESUtil.AESEncode("111111"));
            int count = ucenterUserMapper.insertSelective(ucenterUser);

            //保存用户身份
            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
            ucenterUserIdentity.setUserId(ucenterUser.getUserId());
            ucenterUserIdentity.setUsertypeId(Integer.parseInt(ucenterUser.getDescription()));
            //身份类型
            ucenterUserIdentity.setRelationCode(ucenterUser.getSchoolCode());
            ucenterUserIdentity.setRelationName(ucenterSchool.getSchoolName());
            ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);

            stringBuffer.append(" 新增用户、用户身份 |");
            flag = "1";
        } else {
            if(ucenterUsers.size()==1){
                if(ucenterUsers.get(0).getFullname()!=null&&ucenterUsers.get(0).getFullname().equals(ucenterUser.getFullname())){
                    stringBuffer.append(" 用户已经存在(用户已经存在) |");
                    flag = "1";
                }else{
                    stringBuffer.append(" 用户名不相同 |");
                    flag = "0";
                }
            }else{
                stringBuffer.append(" 手机号在用户表中出现多次:"+ucenterUsers.size()+" |");
                flag = "0";
            }

        }
        return flag;
    }
}
