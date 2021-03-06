package com.lnsoft.response.error;

import com.lnsoft.response.ReturnResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chr on 2018/12/30/0030.
 */
@ControllerAdvice//https://www.cnblogs.com/lenve/p/10748453.html
//@RestControllerAdvice//异常处理增强controller,一般用于全局异常处理,配合@ExceptionHandler一起使用
public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    //定义ExceptionHandler解决未被Controller层吸收的Exception
    @ExceptionHandler(Exception.class)//定义拦截的类型
    @ResponseStatus(HttpStatus.OK)//自定义返回状态码,异常返回也处理为200,只要是接口响应就是200,除非接口无法返回(或者使用500代表返回的信息为异常信息)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {//该ex就是未被吸收的exception
        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof ResponseException) {//如果该异常是自己定义的异常
            ResponseException responseException = (ResponseException) ex;
            responseData.put("errorCode", responseException.getErrorCode());
            responseData.put("errorMessage", responseException.getErrorMessage());
        } else {//如果该异常不是自己定义的
            responseData.put("errorCode", EnumError.UNKNOW_ERROR.getErrorCode());
            responseData.put("errorMessage", EnumError.UNKNOW_ERROR.getErrorMessage());
        }
        ex.printStackTrace();
        return ReturnResult.create(responseData, "fail");

    }
}
