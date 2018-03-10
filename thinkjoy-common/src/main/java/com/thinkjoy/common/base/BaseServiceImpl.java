package com.thinkjoy.common.base;

import com.github.pagehelper.PageHelper;
import com.thinkjoy.common.db.DataSourceEnum;
import com.thinkjoy.common.db.DynamicDataSource;
import com.thinkjoy.common.enumUtil.TableDescriptioin;
import com.thinkjoy.common.util.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataIntegrityViolationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 实现BaseService抽象类
 * Created by  on 2017/01/07.
 */
public abstract class BaseServiceImpl<Mapper, Record, Example> implements BaseService<Record, Example> {

    public Mapper mapper;

    @Override
    public int countByExample(Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method countByExample = mapper.getClass().getDeclaredMethod("countByExample", example.getClass());
            Object result = countByExample.invoke(mapper, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int deleteByExample(Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method deleteByExample = mapper.getClass().getDeclaredMethod("deleteByExample", example.getClass());
            Object result = deleteByExample.invoke(mapper, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {
                    throw new RuntimeException("请删除关联数据！");
                }
            }
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
            Object result = deleteByPrimaryKey.invoke(mapper, id);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {
                    throw new RuntimeException("请删除关联数据！");
                }
            }
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int insert(Record record) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method insert = mapper.getClass().getDeclaredMethod("insert", record.getClass());
            Object result = insert.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int insertSelective(Record record) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method insertSelective = mapper.getClass().getDeclaredMethod("insertSelective", record.getClass());
            Object result = insertSelective.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public List<Record> selectByExampleWithBLOBs(Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
            Object result = selectByExampleWithBLOBs.invoke(mapper, example);
            return (List<Record>) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public List<Record> selectByExample(Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
            Object result = selectByExample.invoke(mapper, example);
            return (List<Record>) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public List<Record> selectByExampleWithBLOBsForStartPage(Example example, Integer pageNum, Integer pageSize) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
            PageHelper.startPage(pageNum, pageSize, false);
            Object result = selectByExampleWithBLOBs.invoke(mapper, example);
            return (List<Record>) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public List<Record> selectByExampleForStartPage(Example example, Integer pageNum, Integer pageSize) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
            PageHelper.startPage(pageNum, pageSize, false);
            Object result = selectByExample.invoke(mapper, example);
            return (List<Record>) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public List<Record> selectByExampleWithBLOBsForOffsetPage(Example example, Integer offset, Integer limit) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
            PageHelper.offsetPage(offset, limit, false);
            Object result = selectByExampleWithBLOBs.invoke(mapper, example);
            return (List<Record>) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
            PageHelper.offsetPage(offset, limit, false);
            Object result = selectByExample.invoke(mapper, example);
            return (List<Record>) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public Record selectFirstByExample(Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
            List<Record> result = (List<Record>) selectByExample.invoke(mapper, example);
            if (null != result && result.size() > 0) {
                return result.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public Record selectFirstByExampleWithBLOBs(Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
            List<Record> result = (List<Record>) selectByExampleWithBLOBs.invoke(mapper, example);
            if (null != result && result.size() > 0) {
                return result.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public Record selectByPrimaryKey(Integer id) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
            Method selectByPrimaryKey = mapper.getClass().getDeclaredMethod("selectByPrimaryKey", id.getClass());
            Object result = selectByPrimaryKey.invoke(mapper, id);
            return (Record) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return null;
    }

    @Override
    public int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method updateByExampleSelective = mapper.getClass().getDeclaredMethod("updateByExampleSelective", record.getClass(), example.getClass());
            Object result = updateByExampleSelective.invoke(mapper, record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int updateByExampleWithBLOBs(@Param("record") Record record, @Param("example") Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method updateByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("updateByExampleWithBLOBs", record.getClass(), example.getClass());
            Object result = updateByExampleWithBLOBs.invoke(mapper, record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int updateByExample(@Param("record") Record record, @Param("example") Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method updateByExample = mapper.getClass().getDeclaredMethod("updateByExample", record.getClass(), example.getClass());
            Object result = updateByExample.invoke(mapper, record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method updateByPrimaryKeySelective = mapper.getClass().getDeclaredMethod("updateByPrimaryKeySelective", record.getClass());
            Object result = updateByPrimaryKeySelective.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Record record) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method updateByPrimaryKeyWithBLOBs = mapper.getClass().getDeclaredMethod("updateByPrimaryKeyWithBLOBs", record.getClass());
            Object result = updateByPrimaryKeyWithBLOBs.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Record record) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            Method updateByPrimaryKey = mapper.getClass().getDeclaredMethod("updateByPrimaryKey", record.getClass());
            Object result = updateByPrimaryKey.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        try {
            if (StringUtils.isBlank(ids)) {
                return 0;
            }
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            String[] idArray = ids.split("-");
            int count = 0;
            for (String idStr : idArray) {
                if (StringUtils.isBlank(idStr)) {
                    continue;
                }
                Integer id = Integer.parseInt(idStr);
                Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
                Object result = deleteByPrimaryKey.invoke(mapper, id);
                count += Integer.parseInt(String.valueOf(result));
            }
            return count;
        } catch (Exception e) {

            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {

                    String res = handleException(e);
                    throw new RuntimeException(res);
                }
            }
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }


    /**
     * 删除基础数据,包含中间表
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteByIds(String ids) {
        try {
            if (StringUtils.isBlank(ids)) {
                return 0;
            }
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            String[] idArray = ids.split("-");
            int count = 0;
            for (String idStr : idArray) {
                if (StringUtils.isBlank(idStr)) {
                    continue;
                }

                Method deleteById = mapper.getClass().getDeclaredMethod("deleteById", idStr.getClass());

                Object result = deleteById.invoke(mapper, idStr);

                count += Integer.parseInt(String.valueOf(result));
            }
            return count;

        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {

                    String res = handleException(e);
                    throw new RuntimeException(res);

                }
            }
            DynamicDataSource.clearDataSource();
            return 0;
        }
    }

    public String handleException(Exception e) {
        String message = ((InvocationTargetException) e).getTargetException().getMessage();

        try {
            String fails = message.substring(message.indexOf("fails"));
            int index1 = fails.indexOf(".");
            int index2 = fails.indexOf(",");

            String table = fails.substring(index1 + 2, index2 - 1);

            TableDescriptioin[] values = TableDescriptioin.values();

            for (TableDescriptioin t : values) {
                if (t.gettName().equals(table)) {
                    return "请先取消: " + t.gettDes() + " 中与之关联的数据!";
                }
            }
        } catch (Exception e1) {

        }
        return "请删除关联数据!";
    }

    @Override
    public void initMapper() {
        this.mapper = SpringContextUtil.getBean(getMapperClass());
    }

    /**
     * 获取类泛型class
     *
     * @return
     */
    public Class<Mapper> getMapperClass() {
        return (Class<Mapper>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}