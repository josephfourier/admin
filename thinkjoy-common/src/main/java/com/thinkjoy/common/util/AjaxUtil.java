package com.thinkjoy.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: AJAX交互响应工具类
 * @Date 2014-2-28-下午3:57:03
 * @Author: qizai
 * @Version: V1.0.0
 */
public class AjaxUtil {
    // 状态码
    private final static String STATUS_CODE_SUCCESS = "200";
    private final static String STATUS_CODE_FAIL = "300";
    private final static String STATUS_CODE_TIMEOUT = "301";
    // 批量删除提示信息页面 title 不准确 20160307
    private final static String STATUS_CODE_INFO = "600";


    public final static String PAGE_FIX = "w_";
    private final static String KEY_STATUSCODE = "statusCode";
    private final static String KEY_MESSAGE = "message";
    private final static String KEY_CALLBACKTYPE = "callbackType";
    private final static String KEY_NAVTABID = "navTabId";
    private final static String AjaxDoneNavTab = "navTabAjaxDone";
    private final static String DialogAjaxDone = "dialogAjaxDone";
    private final static String AjaxDoneCloseCurrent = "closeCurrent";


    /**
     * 成功返回响应。默认{@link #STATUSCODE}为200
     *
     * @param message 成功提示信息
     * @return
     */
    public static Map<String, Object> ok(String message) {
        Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
        returnMap.put(KEY_STATUSCODE, STATUS_CODE_SUCCESS);
        returnMap.put(KEY_MESSAGE, message);
        return returnMap;
    }

    /**
     * 弹出层请求响应 {@value #AjaxDoneCloseCurrent}
     *
     * @param TAB     刷新的TabId
     * @param message 操作成功提示信息，默认为：操作成功！
     * @return
     */
    public static Map<String, Object> okDialog(String TAB, String... message) {
        return ok0(TAB, AjaxDoneCloseCurrent, message);
    }


    public static Map<String, Object> okDialogForIframe(String TAB, String... message) {
        return ok0(TAB, DialogAjaxDone, message);
    }

    /**
     * 删除或批量删除请求响应 {@value #AjaxDoneNavTab}
     *
     * @param TAB     刷新的TabId
     * @param message 操作成功提示信息,默认为：操作成功！
     * @return
     */
    public static Map<String, Object> okTab(String TAB, String... message) {
        return ok0(TAB, AjaxDoneNavTab, message);
    }

    private static Map<String, Object> ok0(String modelName,
                                           String callBackType, String... message) {
        Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
        returnMap.put(KEY_STATUSCODE, STATUS_CODE_SUCCESS);
        returnMap.put(KEY_CALLBACKTYPE, callBackType);
        if (null == message || message.length == 0) {
            returnMap.put(KEY_MESSAGE, "操作成功！");
        } else {
            returnMap.put(KEY_MESSAGE, message[0]);
        }
        returnMap.put(KEY_NAVTABID, PAGE_FIX + modelName);
        if (callBackType.equals(DialogAjaxDone)) {
            returnMap.put("forwardUrl", null);
        }
        return returnMap;
    }
    /**
     * 删除或批量删除请求响应 {@value #AjaxDoneNavTab}
     *
     * @param TAB     刷新的TabId
     * @param message 操作成功提示信息,默认为：操作成功！
     * @return
     */
    public static Map<String, Object> okTabInfo(String TAB, String... message) {
        return okInfo(TAB, AjaxDoneNavTab, message);
    }

    private static Map<String, Object> okInfo(String modelName,
                                           String callBackType, String... message) {
        Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
        returnMap.put(KEY_STATUSCODE, STATUS_CODE_INFO);
        returnMap.put(KEY_CALLBACKTYPE, callBackType);
        if (null == message || message.length == 0) {
            returnMap.put(KEY_MESSAGE, "操作成功！");
        } else {
            returnMap.put(KEY_MESSAGE, message[0]);
        }
        returnMap.put(KEY_NAVTABID, PAGE_FIX + modelName);
        if (callBackType.equals(DialogAjaxDone)) {
            returnMap.put("forwardUrl", null);
        }
        return returnMap;
    }
    /**
     * 出错返回响应。默认{@link #STATUSCODE}为300
     *
     * @param message 出错提示信息
     * @return
     */
    public static Map<String, Object> error(String message) {
        Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
        returnMap.put(KEY_STATUSCODE, STATUS_CODE_FAIL);
        returnMap.put(KEY_MESSAGE, message);
        return returnMap;
    }
    /**
     * 出错返回响应。默认{@link #STATUS_CODE_FAIL}为300
     *
     * @param message 出错提示信息
     * @param callBackType 回调
     * @return
     */
    public static Map<String, Object> error0(String modelName, String callBackType, String message) {
        Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
        returnMap.put(KEY_STATUSCODE, STATUS_CODE_FAIL);
        returnMap.put(KEY_MESSAGE, message);
        returnMap.put(KEY_NAVTABID, PAGE_FIX + modelName);
        returnMap.put(KEY_CALLBACKTYPE, callBackType);
        return returnMap;
    }
    /**
     * 出错返回响应。默认{@link #STATUSCODE}为300
     *
     * @param message 出错提示信息
     * @return
     */
    public static Map<String, Object> error(String modelName, String message){
        return error0(modelName, AjaxDoneCloseCurrent,message);
    }

    /**
     * 超时返回响应。默认{@link #STATUSCODE}为301
     *
     * @param message 出错提示信息
     * @return
     */
    public static Map<String, Object> timeOut(String message) {
        Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
        returnMap.put(KEY_STATUSCODE, STATUS_CODE_TIMEOUT);
        returnMap.put(KEY_MESSAGE, message);
        return returnMap;
    }
}
