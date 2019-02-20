package com.lnsoft.service.impl;

import com.lnsoft.dataobject.UserInfoDO;
import com.lnsoft.dataobject.UserPasswordDO;
import com.lnsoft.mapper.UserInfoDOMapper;
import com.lnsoft.mapper.UserPasswordDOMapper;
import com.lnsoft.response.error.EnumError;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.UserService;
import com.lnsoft.service.model.UserModel;
import com.lnsoft.validator.ValidationResult;
import com.lnsoft.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Seconds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * Created by chr on 2018/12/30/0030.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDOMapper userInfoDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ValidatorImpl validator;


    @Override
    public UserModel getUserById(Integer id) {
        UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(id);
        if (userInfoDO == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserInfoId(userInfoDO.getId());

        return convertFromDataObject(userInfoDO, userPasswordDO);
    }

    //存手机号-验证码：redis
    @Override
    public void userLoginInfoByRedis(String telPhone, String mobile_code) {
        stringRedisTemplate.opsForValue().set(telPhone, mobile_code,7, TimeUnit.DAYS);
    }

    @Override
    public String getUserOtpCodeByRedis(String telPhone) {
        return stringRedisTemplate.opsForValue().get(telPhone);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws ResponseException {
        if (userModel == null) {
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR);
        }
        //原始的校验代码
//        if (StringUtils.isEmpty((userModel.getName()))
//                || userModel.getAge() == null //
//                || userModel.getGender() == null //
//                || userModel.getTelphone() == null) {
//            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR);
//        }
        //使用validator的校验代码
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR,//
                    result.getErrorMessage());//该errorMessage是ValidationResult中自定义的get方法，为了获取错误属性的message
        }


        UserInfoDO userInfoDO = convertInfoFromModel(userModel);
        /**
         * 为什么要使用insertSelective，该方法与普通的insert不同
         * 这个方法会判断insert的字段是否为null，不为null，就insert
         * 为null，则不修改数据库的数据
         */
        try {
            userInfoDOMapper.insertSelective(userInfoDO);
        } catch (DuplicateKeyException d) {
            //因为Duplicate entry：telphone_unique_index，设置了唯一约束，
            //如果手机号码重复，为DuplicateKeyException，只要捕获这个异常信息，返回给用户该错误对应的原因即可
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR, "手机号已重复注册");
        }
        /**
         * 全部用id的传递：userInfoDo的id属性传递给userModel的id属性再传递给userPasswod的id属性，从而达到级联
         * 这是为了取出userInfoDo里的id属性赋值给usermodel，然后usermodel在copy给userPassword，从而达到了关联
         */
        //userModel.setId(userInfoDO.getId());
        /**
         * 除了上述级联，还可以用userInfoDo的id属性传递给userModel的userId属性，在传递给userPassword的userId属性
         */
        userModel.setUserId(userInfoDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }

    @Override
    public UserModel validateLogin(String telPhone, String encrptPassword) throws ResponseException {
        //通过用户手机获取用户信息
        UserInfoDO userInfoDO = userInfoDOMapper.selectByTelPhone(telPhone);
        if (userInfoDO == null) {
            throw new ResponseException(EnumError.USER_LOGIN_FAIL);
        }
        //根据用户id拿取用户密码,注意userId不是主键
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserInfoId(userInfoDO.getId());
        UserModel userModel = convertFromDataObject(userInfoDO, userPasswordDO);
        //对比用户信息的加密密码和用户输入密码一致:lang3
        if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
            throw new ResponseException(EnumError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    //userModel->userPasswordDo
    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDo = new UserPasswordDO();
        userPasswordDo.setEncrptPassword(userModel.getEncrptPassword());
        //用户级联:这里是userInfoDo的id传给了userModel，userpassword在从usermodel获取
        //userPasswordDo.setUserId(userModel.getId());
        //这个是利用userId来传递
        BeanUtils.copyProperties(userModel, userPasswordDo);
        return userPasswordDo;
    }

    //userModel->userInfoDo
    private UserInfoDO convertInfoFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();

        BeanUtils.copyProperties(userModel, userInfoDO);
        return userInfoDO;
    }

    //处理格式:userInfoDo和userPasswordDo转化为userModel
    private UserModel convertFromDataObject(UserInfoDO userInfoDO, UserPasswordDO userPasswordDO) {
        if (userInfoDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        //userInfoDo,userModel
        //Object source, Object target
        //该方法：把userInfoDO的属性，copy到UserModel内
        BeanUtils.copyProperties(userInfoDO, userModel);
        if (userPasswordDO != null) {
            //因为id属性重复
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}
