package com.thinkjoy.ucenter.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterAreaMapper;
import com.thinkjoy.ucenter.dao.model.UcenterArea;
import com.thinkjoy.ucenter.dao.model.UcenterAreaExample;
import com.thinkjoy.ucenter.rpc.api.UcenterAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UcenterAreaService实现
* Created by xufei on 2017/7/26.
*/
@Service
@Transactional
@BaseService
public class UcenterAreaServiceImpl extends BaseServiceImpl<UcenterAreaMapper, UcenterArea, UcenterAreaExample> implements UcenterAreaService {

    private static Logger _log = LoggerFactory.getLogger(UcenterAreaServiceImpl.class);

    @Autowired
    UcenterAreaMapper ucenterAreaMapper;

    @Override
    public JSONArray getAreaTree() {


        String message="[\n" +
                "\t{id:1, pId:0, name:\"[core] 基本功能 演示\", open:false},\n" +
                "\t{id:101, pId:1, name:\"最简单的树 --  标准 JSON 数据\", iconSkin:\"menu\", file:\"core/standardData\"},\n" +
                "\t{id:102, pId:1, name:\"最简单的树 --  简单 JSON 数据\", iconSkin:\"menu\", file:\"core/simpleData\"},\n" +
                "\t{id:103, pId:1, name:\"不显示 连接线\", iconSkin:\"menu\", file:\"core/noline\"},\n" +
                "\t{id:104, pId:1, name:\"不显示 节点 图标\", iconSkin:\"menu\", file:\"core/noicon\"},\n" +
                "\t{id:105, pId:1, name:\"自定义图标 --  icon 属性\", iconSkin:\"menu\", file:\"core/custom_icon\"},\n" +
                "\t{id:106, pId:1, name:\"自定义图标 --  iconSkin 属性\", iconSkin:\"menu\", file:\"core/custom_iconSkin\"},\n" +
                "\t{id:107, pId:1, name:\"自定义字体\", iconSkin:\"menu\", file:\"core/custom_font\"},\n" +
                "\t{id:115, pId:1, name:\"超链接演示\", iconSkin:\"menu\", file:\"core/url\"},\n" +
                "\t{id:108, pId:1, name:\"异步加载 节点数据\", iconSkin:\"menu\", file:\"core/async\"},\n" +
                "\t{id:109, pId:1, name:\"用 zTree 方法 异步加载 节点数据\", iconSkin:\"menu\", file:\"core/async_fun\"},\n" +
                "\t{id:110, pId:1, name:\"用 zTree 方法 更新 节点数据\", iconSkin:\"menu\", file:\"core/update_fun\"},\n" +
                "\t{id:111, pId:1, name:\"单击 节点 控制\", iconSkin:\"menu\", file:\"core/click\"},\n" +
                "\t{id:112, pId:1, name:\"展开 / 折叠 父节点 控制\", iconSkin:\"menu\", file:\"core/expand\"},\n" +
                "\t{id:113, pId:1, name:\"根据 参数 查找 节点\", iconSkin:\"menu\", file:\"core/searchNodes\"},\n" +
                "\t{id:114, pId:1, name:\"其他 鼠标 事件监听\", iconSkin:\"menu\", file:\"core/otherMouse\"},\n" +
                "\n" +
                "\t{id:2, pId:0, name:\"[excheck] 复/单选框功能 演示\", open:false},\n" +
                "\t{id:201, pId:2, name:\"Checkbox 勾选操作\", iconSkin:\"menu\", file:\"excheck/checkbox\"},\n" +
                "\t{id:206, pId:2, name:\"Checkbox nocheck 演示\", iconSkin:\"menu\", file:\"excheck/checkbox_nocheck\"},\n" +
                "\t{id:207, pId:2, name:\"Checkbox chkDisabled 演示\", iconSkin:\"menu\", file:\"excheck/checkbox_chkDisabled\"},\n" +
                "\t{id:208, pId:2, name:\"Checkbox halfCheck 演示\", iconSkin:\"menu\", file:\"excheck/checkbox_halfCheck\"},\n" +
                "\t{id:202, pId:2, name:\"Checkbox 勾选统计\", iconSkin:\"menu\", file:\"excheck/checkbox_count\"},\n" +
                "\t{id:203, pId:2, name:\"用 zTree 方法 勾选 Checkbox\", iconSkin:\"menu\", file:\"excheck/checkbox_fun\"},\n" +
                "\t{id:204, pId:2, name:\"Radio 勾选操作\", iconSkin:\"menu\", file:\"excheck/radio\"},\n" +
                "\t{id:209, pId:2, name:\"Radio nocheck 演示\", iconSkin:\"menu\", file:\"excheck/radio_nocheck\"},\n" +
                "\t{id:210, pId:2, name:\"Radio chkDisabled 演示\", iconSkin:\"menu\", file:\"excheck/radio_chkDisabled\"},\n" +
                "\t{id:211, pId:2, name:\"Radio halfCheck 演示\", iconSkin:\"menu\", file:\"excheck/radio_halfCheck\"},\n" +
                "\t{id:205, pId:2, name:\"用 zTree 方法 勾选 Radio\", iconSkin:\"menu\", file:\"excheck/radio_fun\"},\n" +
                "\t{id:212, pId:2, name:\"\", blank:true},\n" +
                "\t{id:213, pId:2, name:\"\", blank:true},\n" +
                "\t{id:214, pId:2, name:\"\", blank:true},\n" +
                "\t{id:215, pId:2, name:\"\", blank:true},\n" +
                "\n" +
                "\t{id:3, pId:0, name:\"[exedit] 编辑功能 演示\", open:false},\n" +
                "\t{id:301, pId:3, name:\"拖拽 节点 基本控制\", iconSkin:\"menu\", file:\"exedit/drag\"},\n" +
                "\t{id:302, pId:3, name:\"拖拽 节点 高级控制\", iconSkin:\"menu\", file:\"exedit/drag_super\"},\n" +
                "\t{id:303, pId:3, name:\"用 zTree 方法 移动 / 复制 节点\", iconSkin:\"menu\", file:\"exedit/drag_fun\"},\n" +
                "\t{id:304, pId:3, name:\"基本 增 / 删 / 改 节点\", iconSkin:\"menu\", file:\"exedit/edit\"},\n" +
                "\t{id:305, pId:3, name:\"高级 增 / 删 / 改 节点\", iconSkin:\"menu\", file:\"exedit/edit_super\"},\n" +
                "\t{id:306, pId:3, name:\"用 zTree 方法 增 / 删 / 改 节点\", iconSkin:\"menu\", file:\"exedit/edit_fun\"},\n" +
                "\t{id:307, pId:3, name:\"异步加载 & 编辑功能 共存\", iconSkin:\"menu\", file:\"exedit/async_edit\"},\n" +
                "\t{id:308, pId:3, name:\"多棵树之间 的 数据交互\", iconSkin:\"menu\", file:\"exedit/multiTree\"},\n" +
                "\t{id:309, pId:3, name:\"\", blank:true},\n" +
                "\t{id:310, pId:3, name:\"\", blank:true},\n" +
                "\t{id:311, pId:3, name:\"\", blank:true},\n" +
                "\t{id:312, pId:3, name:\"\", blank:true},\n" +
                "\t{id:313, pId:3, name:\"\", blank:true},\n" +
                "\t{id:314, pId:3, name:\"\", blank:true},\n" +
                "\t{id:315, pId:3, name:\"\", blank:true},\n" +
                "\n" +
                "\t{id:4, pId:0, name:\"大数据量 演示\", open:false},\n" +
                "\t{id:401, pId:4, name:\"一次性加载大数据量\", iconSkin:\"menu\", file:\"bigdata/common\"},\n" +
                "\t{id:402, pId:4, name:\"分批异步加载大数据量\", iconSkin:\"menu\", file:\"bigdata/diy_async\"},\n" +
                "\t{id:403, pId:4, name:\"分页加载大数据量\", iconSkin:\"menu\", file:\"bigdata/page\"},\n" +
                "\t{id:404, pId:4, name:\"\", blank:true},\n" +
                "\t{id:405, pId:4, name:\"\", blank:true},\n" +
                "\t{id:406, pId:4, name:\"\", blank:true},\n" +
                "\t{id:407, pId:4, name:\"\", blank:true},\n" +
                "\t{id:408, pId:4, name:\"\", blank:true},\n" +
                "\t{id:409, pId:4, name:\"\", blank:true},\n" +
                "\t{id:410, pId:4, name:\"\", blank:true},\n" +
                "\t{id:411, pId:4, name:\"\", blank:true},\n" +
                "\t{id:412, pId:4, name:\"\", blank:true},\n" +
                "\t{id:413, pId:4, name:\"\", blank:true},\n" +
                "\t{id:414, pId:4, name:\"\", blank:true},\n" +
                "\t{id:415, pId:4, name:\"\", blank:true},\n" +
                "\n" +
                "\t{id:5, pId:0, name:\"组合功能 演示\", open:false},\n" +
                "\t{id:501, pId:5, name:\"冻结根节点\", iconSkin:\"menu\", file:\"super/oneroot\"},\n" +
                "\t{id:502, pId:5, name:\"单击展开/折叠节点\", iconSkin:\"menu\", file:\"super/oneclick\"},\n" +
                "\t{id:503, pId:5, name:\"保持展开单一路径\", iconSkin:\"menu\", file:\"super/singlepath\"},\n" +
                "\t{id:504, pId:5, name:\"添加 自定义控件\", iconSkin:\"menu\", file:\"super/diydom\"},\n" +
                "\t{id:505, pId:5, name:\"checkbox / radio 共存\", iconSkin:\"menu\", file:\"super/checkbox_radio\"},\n" +
                "\t{id:506, pId:5, name:\"左侧菜单\", iconSkin:\"menu\", file:\"super/left_menu\"},\n" +
                "\t{id:513, pId:5, name:\"OutLook 风格\", iconSkin:\"menu\", file:\"super/left_menuForOutLook\"},\n" +
                "\t{id:514, pId:5, name:\"Metro 风格\", iconSkin:\"menu\", file:\"super/metro\"},\n" +
                "\t{id:515, pId:5, name:\"Awesome 风格\", iconSkin:\"menu\", file:\"super/awesome\"},\n" +
                "\t{id:507, pId:5, name:\"下拉菜单\", iconSkin:\"menu\", file:\"super/select_menu\"},\n" +
                "\t{id:509, pId:5, name:\"带 checkbox 的多选下拉菜单\", iconSkin:\"menu\", file:\"super/select_menu_checkbox\"},\n" +
                "\t{id:510, pId:5, name:\"带 radio 的单选下拉菜单\", iconSkin:\"menu\", file:\"super/select_menu_radio\"},\n" +
                "\t{id:508, pId:5, name:\"右键菜单 的 实现\", iconSkin:\"menu\", file:\"super/rightClickMenu\"},\n" +
                "\t{id:511, pId:5, name:\"与其他 DOM 拖拽互动\", iconSkin:\"menu\", file:\"super/dragWithOther\"},\n" +
                "\t{id:512, pId:5, name:\"异步加载模式下全部展开\", iconSkin:\"menu\", file:\"super/asyncForAll\"},\n" +
                "\t{id:516, pId:5, name:\"\", blank:true},\n" +
                "\n" +
                "\t{id:6, pId:0, name:\"其他扩展功能 演示\", open:false},\n" +
                "\t{id:601, pId:6, name:\"隐藏普通节点\", iconSkin:\"menu\", file:\"exhide/common\"},\n" +
                "\t{id:602, pId:6, name:\"配合 checkbox 的隐藏\", iconSkin:\"menu\", file:\"exhide/checkbox\"},\n" +
                "\t{id:603, pId:6, name:\"配合 radio 的隐藏\", iconSkin:\"menu\", file:\"exhide/radio\"},\n" +
                "\t{id:604, pId:6, name:\"\", blank:true},\n" +
                "\t{id:605, pId:6, name:\"\", blank:true},\n" +
                "\t{id:606, pId:6, name:\"\", blank:true},\n" +
                "\t{id:607, pId:6, name:\"\", blank:true},\n" +
                "\t{id:608, pId:6, name:\"\", blank:true},\n" +
                "\t{id:609, pId:6, name:\"\", blank:true},\n" +
                "\t{id:610, pId:6, name:\"\", blank:true},\n" +
                "\t{id:611, pId:6, name:\"\", blank:true},\n" +
                "\t{id:612, pId:6, name:\"\", blank:true},\n" +
                "\t{id:613, pId:6, name:\"\", blank:true},\n" +
                "\t{id:614, pId:6, name:\"\", blank:true},\n" +
                "\t{id:615, pId:6, name:\"\", blank:true}\n" +
                "]";


         JSONArray json = new JSONArray();
         List<UcenterArea> list=ucenterAreaMapper.getAreaTree();
         for(UcenterArea obj:list ){
                 JSONObject node = new JSONObject();
                 node.put("id",obj.getAreaCode());
                 node.put("pId",obj.getPcode());
                 node.put("name",obj.getAreaName());
                 node.put("level",obj.getAreaType());
                 json.add(node);
         }
         return json;
    }

	public JSONArray getschoolTree(String areaCode) {
		String message="[\n" +
				"\t{id:1, pId:0, name:\"[core] 基本功能 演示\", open:false},\n" +
				"\t{id:101, pId:1, name:\"最简单的树 --  标准 JSON 数据\", iconSkin:\"menu\", file:\"core/standardData\"},\n" +
				"\t{id:102, pId:1, name:\"最简单的树 --  简单 JSON 数据\", iconSkin:\"menu\", file:\"core/simpleData\"},\n" +
				"\t{id:103, pId:1, name:\"不显示 连接线\", iconSkin:\"menu\", file:\"core/noline\"},\n" +
				"\t{id:104, pId:1, name:\"不显示 节点 图标\", iconSkin:\"menu\", file:\"core/noicon\"},\n" +
				"\t{id:105, pId:1, name:\"自定义图标 --  icon 属性\", iconSkin:\"menu\", file:\"core/custom_icon\"},\n" +
				"\t{id:106, pId:1, name:\"自定义图标 --  iconSkin 属性\", iconSkin:\"menu\", file:\"core/custom_iconSkin\"},\n" +
				"\t{id:107, pId:1, name:\"自定义字体\", iconSkin:\"menu\", file:\"core/custom_font\"},\n" +
				"\t{id:115, pId:1, name:\"超链接演示\", iconSkin:\"menu\", file:\"core/url\"},\n" +
				"\t{id:108, pId:1, name:\"异步加载 节点数据\", iconSkin:\"menu\", file:\"core/async\"},\n" +
				"\t{id:109, pId:1, name:\"用 zTree 方法 异步加载 节点数据\", iconSkin:\"menu\", file:\"core/async_fun\"},\n" +
				"\t{id:110, pId:1, name:\"用 zTree 方法 更新 节点数据\", iconSkin:\"menu\", file:\"core/update_fun\"},\n" +
				"\t{id:111, pId:1, name:\"单击 节点 控制\", iconSkin:\"menu\", file:\"core/click\"},\n" +
				"\t{id:112, pId:1, name:\"展开 / 折叠 父节点 控制\", iconSkin:\"menu\", file:\"core/expand\"},\n" +
				"\t{id:113, pId:1, name:\"根据 参数 查找 节点\", iconSkin:\"menu\", file:\"core/searchNodes\"},\n" +
				"\t{id:114, pId:1, name:\"其他 鼠标 事件监听\", iconSkin:\"menu\", file:\"core/otherMouse\"},\n" +
				"\n" +
				"\t{id:2, pId:0, name:\"[excheck] 复/单选框功能 演示\", open:false},\n" +
				"\t{id:201, pId:2, name:\"Checkbox 勾选操作\", iconSkin:\"menu\", file:\"excheck/checkbox\"},\n" +
				"\t{id:206, pId:2, name:\"Checkbox nocheck 演示\", iconSkin:\"menu\", file:\"excheck/checkbox_nocheck\"},\n" +
				"\t{id:207, pId:2, name:\"Checkbox chkDisabled 演示\", iconSkin:\"menu\", file:\"excheck/checkbox_chkDisabled\"},\n" +
				"\t{id:208, pId:2, name:\"Checkbox halfCheck 演示\", iconSkin:\"menu\", file:\"excheck/checkbox_halfCheck\"},\n" +
				"\t{id:202, pId:2, name:\"Checkbox 勾选统计\", iconSkin:\"menu\", file:\"excheck/checkbox_count\"},\n" +
				"\t{id:203, pId:2, name:\"用 zTree 方法 勾选 Checkbox\", iconSkin:\"menu\", file:\"excheck/checkbox_fun\"},\n" +
				"\t{id:204, pId:2, name:\"Radio 勾选操作\", iconSkin:\"menu\", file:\"excheck/radio\"},\n" +
				"\t{id:209, pId:2, name:\"Radio nocheck 演示\", iconSkin:\"menu\", file:\"excheck/radio_nocheck\"},\n" +
				"\t{id:210, pId:2, name:\"Radio chkDisabled 演示\", iconSkin:\"menu\", file:\"excheck/radio_chkDisabled\"},\n" +
				"\t{id:211, pId:2, name:\"Radio halfCheck 演示\", iconSkin:\"menu\", file:\"excheck/radio_halfCheck\"},\n" +
				"\t{id:205, pId:2, name:\"用 zTree 方法 勾选 Radio\", iconSkin:\"menu\", file:\"excheck/radio_fun\"},\n" +
				"\t{id:212, pId:2, name:\"\", blank:true},\n" +
				"\t{id:213, pId:2, name:\"\", blank:true},\n" +
				"\t{id:214, pId:2, name:\"\", blank:true},\n" +
				"\t{id:215, pId:2, name:\"\", blank:true},\n" +
				"\n" +
				"\t{id:3, pId:0, name:\"[exedit] 编辑功能 演示\", open:false},\n" +
				"\t{id:301, pId:3, name:\"拖拽 节点 基本控制\", iconSkin:\"menu\", file:\"exedit/drag\"},\n" +
				"\t{id:302, pId:3, name:\"拖拽 节点 高级控制\", iconSkin:\"menu\", file:\"exedit/drag_super\"},\n" +
				"\t{id:303, pId:3, name:\"用 zTree 方法 移动 / 复制 节点\", iconSkin:\"menu\", file:\"exedit/drag_fun\"},\n" +
				"\t{id:304, pId:3, name:\"基本 增 / 删 / 改 节点\", iconSkin:\"menu\", file:\"exedit/edit\"},\n" +
				"\t{id:305, pId:3, name:\"高级 增 / 删 / 改 节点\", iconSkin:\"menu\", file:\"exedit/edit_super\"},\n" +
				"\t{id:306, pId:3, name:\"用 zTree 方法 增 / 删 / 改 节点\", iconSkin:\"menu\", file:\"exedit/edit_fun\"},\n" +
				"\t{id:307, pId:3, name:\"异步加载 & 编辑功能 共存\", iconSkin:\"menu\", file:\"exedit/async_edit\"},\n" +
				"\t{id:308, pId:3, name:\"多棵树之间 的 数据交互\", iconSkin:\"menu\", file:\"exedit/multiTree\"},\n" +
				"\t{id:309, pId:3, name:\"\", blank:true},\n" +
				"\t{id:310, pId:3, name:\"\", blank:true},\n" +
				"\t{id:311, pId:3, name:\"\", blank:true},\n" +
				"\t{id:312, pId:3, name:\"\", blank:true},\n" +
				"\t{id:313, pId:3, name:\"\", blank:true},\n" +
				"\t{id:314, pId:3, name:\"\", blank:true},\n" +
				"\t{id:315, pId:3, name:\"\", blank:true},\n" +
				"\n" +
				"\t{id:4, pId:0, name:\"大数据量 演示\", open:false},\n" +
				"\t{id:401, pId:4, name:\"一次性加载大数据量\", iconSkin:\"menu\", file:\"bigdata/common\"},\n" +
				"\t{id:402, pId:4, name:\"分批异步加载大数据量\", iconSkin:\"menu\", file:\"bigdata/diy_async\"},\n" +
				"\t{id:403, pId:4, name:\"分页加载大数据量\", iconSkin:\"menu\", file:\"bigdata/page\"},\n" +
				"\t{id:404, pId:4, name:\"\", blank:true},\n" +
				"\t{id:405, pId:4, name:\"\", blank:true},\n" +
				"\t{id:406, pId:4, name:\"\", blank:true},\n" +
				"\t{id:407, pId:4, name:\"\", blank:true},\n" +
				"\t{id:408, pId:4, name:\"\", blank:true},\n" +
				"\t{id:409, pId:4, name:\"\", blank:true},\n" +
				"\t{id:410, pId:4, name:\"\", blank:true},\n" +
				"\t{id:411, pId:4, name:\"\", blank:true},\n" +
				"\t{id:412, pId:4, name:\"\", blank:true},\n" +
				"\t{id:413, pId:4, name:\"\", blank:true},\n" +
				"\t{id:414, pId:4, name:\"\", blank:true},\n" +
				"\t{id:415, pId:4, name:\"\", blank:true},\n" +
				"\n" +
				"\t{id:5, pId:0, name:\"组合功能 演示\", open:false},\n" +
				"\t{id:501, pId:5, name:\"冻结根节点\", iconSkin:\"menu\", file:\"super/oneroot\"},\n" +
				"\t{id:502, pId:5, name:\"单击展开/折叠节点\", iconSkin:\"menu\", file:\"super/oneclick\"},\n" +
				"\t{id:503, pId:5, name:\"保持展开单一路径\", iconSkin:\"menu\", file:\"super/singlepath\"},\n" +
				"\t{id:504, pId:5, name:\"添加 自定义控件\", iconSkin:\"menu\", file:\"super/diydom\"},\n" +
				"\t{id:505, pId:5, name:\"checkbox / radio 共存\", iconSkin:\"menu\", file:\"super/checkbox_radio\"},\n" +
				"\t{id:506, pId:5, name:\"左侧菜单\", iconSkin:\"menu\", file:\"super/left_menu\"},\n" +
				"\t{id:513, pId:5, name:\"OutLook 风格\", iconSkin:\"menu\", file:\"super/left_menuForOutLook\"},\n" +
				"\t{id:514, pId:5, name:\"Metro 风格\", iconSkin:\"menu\", file:\"super/metro\"},\n" +
				"\t{id:515, pId:5, name:\"Awesome 风格\", iconSkin:\"menu\", file:\"super/awesome\"},\n" +
				"\t{id:507, pId:5, name:\"下拉菜单\", iconSkin:\"menu\", file:\"super/select_menu\"},\n" +
				"\t{id:509, pId:5, name:\"带 checkbox 的多选下拉菜单\", iconSkin:\"menu\", file:\"super/select_menu_checkbox\"},\n" +
				"\t{id:510, pId:5, name:\"带 radio 的单选下拉菜单\", iconSkin:\"menu\", file:\"super/select_menu_radio\"},\n" +
				"\t{id:508, pId:5, name:\"右键菜单 的 实现\", iconSkin:\"menu\", file:\"super/rightClickMenu\"},\n" +
				"\t{id:511, pId:5, name:\"与其他 DOM 拖拽互动\", iconSkin:\"menu\", file:\"super/dragWithOther\"},\n" +
				"\t{id:512, pId:5, name:\"异步加载模式下全部展开\", iconSkin:\"menu\", file:\"super/asyncForAll\"},\n" +
				"\t{id:516, pId:5, name:\"\", blank:true},\n" +
				"\n" +
				"\t{id:6, pId:0, name:\"其他扩展功能 演示\", open:false},\n" +
				"\t{id:601, pId:6, name:\"隐藏普通节点\", iconSkin:\"menu\", file:\"exhide/common\"},\n" +
				"\t{id:602, pId:6, name:\"配合 checkbox 的隐藏\", iconSkin:\"menu\", file:\"exhide/checkbox\"},\n" +
				"\t{id:603, pId:6, name:\"配合 radio 的隐藏\", iconSkin:\"menu\", file:\"exhide/radio\"},\n" +
				"\t{id:604, pId:6, name:\"\", blank:true},\n" +
				"\t{id:605, pId:6, name:\"\", blank:true},\n" +
				"\t{id:606, pId:6, name:\"\", blank:true},\n" +
				"\t{id:607, pId:6, name:\"\", blank:true},\n" +
				"\t{id:608, pId:6, name:\"\", blank:true},\n" +
				"\t{id:609, pId:6, name:\"\", blank:true},\n" +
				"\t{id:610, pId:6, name:\"\", blank:true},\n" +
				"\t{id:611, pId:6, name:\"\", blank:true},\n" +
				"\t{id:612, pId:6, name:\"\", blank:true},\n" +
				"\t{id:613, pId:6, name:\"\", blank:true},\n" +
				"\t{id:614, pId:6, name:\"\", blank:true},\n" +
				"\t{id:615, pId:6, name:\"\", blank:true}\n" +
				"]";


		JSONArray json = new JSONArray();
		List<UcenterArea> list=ucenterAreaMapper.getAreaTree();
		for(UcenterArea obj:list ){
			JSONObject node = new JSONObject();
			node.put("id",obj.getAreaCode());
			node.put("pId",obj.getPcode());
			node.put("name",obj.getAreaName());
			node.put("level",obj.getAreaType());
			json.add(node);
		}
		return json;
	}
}