package com.lnsoft.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chr on 2019/1/1/0001.
 */
public class ValidationResult {
    //校验结果是否有错
    private boolean hasErrors = false;

    //存放错误信息的map
    private Map<String, String> errorMessageMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMessageMap() {
        return errorMessageMap;
    }

    public void setErrorMessageMap(Map<String, String> errorMessageMap) {
        this.errorMessageMap = errorMessageMap;
    }

    //实现通用的通过格式化字符串信息获取错误结果的message方法，该方法为了获取错信息的value值，
    //就是Model类注解中message，在UserServiceImpl直接用异常抛出该message当作errorMessage，返回给前端
    public String getErrorMessage() {
        return StringUtils.join(errorMessageMap.values().toArray(), ",");
    }
}
