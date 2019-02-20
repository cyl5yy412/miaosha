package com.lnsoft.response.error;

/**
 * Created by chr on 2018/12/30/0030.
 */

/**
 * 定义该接口，主要是为了可以自定义得到error的信息
 */
public interface CommonError {

    public int getErrorCode();
    public String getErrorMessage();
    public CommonError setErrorMessage(String errorMessage);

}
