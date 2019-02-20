package com.lnsoft.response.error;

import com.lnsoft.response.ReturnResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chr on 2018/12/30/0030.
 */
public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    //定义ExceptionHandler解决未被Controller层吸收的Exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof ResponseException) {
            ResponseException responseException = (ResponseException) ex;
            responseData.put("errorCode", responseException.getErrorCode());
            responseData.put("errorMessage", responseException.getErrorMessage());
        } else {
            responseData.put("errorCode", EnumError.UNKNOW_ERROR.getErrorCode());
            responseData.put("errorMessage", EnumError.UNKNOW_ERROR.getErrorMessage());
        }
        ex.printStackTrace();
        return ReturnResult.create(responseData, "fail");

    }
}
