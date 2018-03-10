package com.thinkjoy.common.base;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidationResult;
import com.thinkjoy.common.util.PropertiesFileUtil;
import com.thinkjoy.common.util.PropertiesUtil;
import com.thinkjoy.exception.BusindessException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * 控制器基类
 *
 * @author wangcheng
 * @date 2017/2/4
 */
public abstract class BaseController<T, S extends BaseService> {

    private final static Logger _log = LoggerFactory.getLogger(BaseController.class);

    protected S getService() {
        return null;
    }

    /**
     * 统一异常处理
     *
     * @param request
     * @param response
     * @param exception
     */
    @ExceptionHandler
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        _log.error("统一异常处理：", exception);
        request.setAttribute("ex", exception);
        if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            request.setAttribute("requestHeader", "ajax");
        }
        // shiro没有权限异常
        if (exception instanceof UnauthorizedException) {
            return "/403.jsp";
        }
        // shiro会话已过期异常
        if (exception instanceof InvalidSessionException) {
            return "/error.jsp";
        }
        return "/error.jsp";
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public Object exceptionHandler1(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        _log.error("统一异常处理：", exception);
        request.setAttribute("ex", exception);
        if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            request.setAttribute("requestHeader", "ajax");
        }
        return new BaseResult(BaseConstants.ResultCode.ERROR, exception.getMessage(), getErrorMsg(exception.getMessage()));
    }
    /**
     * 返回jsp视图
     *
     * @param path
     * @return
     */
    public static String jsp(String path) {
        return path.concat(".jsp");
    }

    /**
     * 返回thymeleaf视图
     *
     * @param path
     * @return
     */
    public static String thymeleaf(String path) {
        String folder = PropertiesUtil.getInstance().getProperties().getProperty("app.name");
        return "/".concat(folder).concat(path).concat(".html");
    }

    /**
     * 获取使用注解时的错误信息提取
     *
     * @param result
     * @return
     */
    public HashMap getErrorMsg(BindingResult result) {
        List<FieldError> errors = result.getFieldErrors();
        StringBuilder strBuilder = new StringBuilder();
        for (FieldError err : errors) {
            strBuilder.append(err.getDefaultMessage()).append("<br/>");
        }

        HashMap<String, String> emap = new HashMap<>();
        emap.put("errorMsg", strBuilder.toString());
        return emap;
    }


    /**
     * 拼接错误信息
     *
     * @param
     * @return
     */
    public HashMap getErrorMsg(String errMsg) {
        HashMap<String, String> emap = new HashMap<>();
        emap.put("errorMsg", errMsg);
        return emap;
    }


    @ModelAttribute("model")
    public T getModel(
            @PathVariable(value = "id", required = false) Integer id) {
        if (id != null && getService() != null) {
            try {
                return (T) getService().selectByPrimaryKey(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
