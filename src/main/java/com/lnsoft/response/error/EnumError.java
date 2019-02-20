package com.lnsoft.response.error;

/**
 * Created by chr on 2018/12/30/0030.
 */

/**
 * 枚举异常错误集
 */
public enum EnumError implements CommonError {

    // 1 开头为通用错误类型：通用错误可以根据业务需求修改，比如一些入参没传需要定义很多错误码，只要定义一个不合法类型，只去改动errorMessage即可
    PARAMETER_VALIDATION_ERROR(10000,"参数不合法"),
    UNKNOW_ERROR(00001,"未知错误"),

    // 2 开头为用户信息相关错误
    USER_NOT_EXISTS(20000,"用户不存在"),
    USER_CANCLE(20001,"用户已注销"),
    USER_LOGIN_FAIL(20002,"用户手机号码或者密码不正确"),
    USER_NOT_LOGIN(20003,"用户暂未登陆"),

    //3 开头为交易信息错误
    STOCK_NOT_ENOUGH(30000,"库存不足")

    ;


    EnumError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    //也可以是String类型的errorCode
    private int errorCode;
    private String errorMessage;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    //错误码改动信息
    @Override
    public CommonError setErrorMessage(String errorMessage) {
        this.errorMessage=errorMessage;
        return this;
    }
}
