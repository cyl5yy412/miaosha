package com.lnsoft.service.model;

import com.lnsoft.dataobject.UserInfoDO;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by chr on 2018/12/30/0030.
 */

/**
 * 用户模型
 * Mo负责业务处理的所有信息，可以继承po，也可以手写
 */
public class UserModel extends UserInfoDO {

    private Integer userId;

    //validator的注解，用来校验信息
    @NotBlank(message = "密码不能为空")
    private String encrptPassword;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }
}
