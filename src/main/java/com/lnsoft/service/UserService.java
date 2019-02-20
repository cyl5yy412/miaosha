package com.lnsoft.service;

import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.model.UserModel;

/**
 * Created by chr on 2018/12/30/0030.
 */

public interface UserService {
    UserModel getUserById(Integer id);

    /**
     * 登陆短信对于联系人存在redis
     * @param telPhone
     * @param mobile_code
     */
    void userLoginInfoByRedis(String telPhone, String mobile_code);

    /**
     * 从redis获取手机号的验证码
     * @param telPhone
     * @return
     */
    String getUserOtpCodeByRedis(String telPhone);

    /**
     * 注册
     * @param userModel
     * @throws ResponseException
     */
    void register(UserModel userModel) throws ResponseException;
    /**
     *    验证登录：telPhone用户手机号，password用户加密的密码
     */
    UserModel validateLogin(String telPhone,String encrptPassword) throws ResponseException;
}
