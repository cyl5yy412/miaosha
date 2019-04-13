package com.lnsoft.controller;

import com.alibaba.druid.util.StringUtils;
import com.lnsoft.controller.viewobject.UserVo;
import com.lnsoft.response.ChrReturnResult;
import com.lnsoft.response.ReturnResult;
import com.lnsoft.response.error.BaseController;
import com.lnsoft.response.error.EnumError;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.UserService;
import com.lnsoft.service.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by chr on 2018/12/30/0030.
 */
@Api(value = "user用户信息", description = "user用户注册/登录/短信验证")
@Controller
@RequestMapping("/user")
//allowCredentials ="true" 需要在前端设置xhrFields属性（格式：xhrFields:{withCredentials:true},），需要前后端呼应起来（前后端都需要设置相对应的属性true），才能授权session跨域共享
//allowedHeaders = "*" 作用：ajax跨域，token放入header从而使session可以跨域
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
//这是ajax跨域的注解，解决web的Access-Control-Allow-Origin，但是session跨域还未解决，需要设置属性
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    //用户登录接口
    @ApiOperation(value = "用户登录", notes = "用户登录：用户名/密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "telPhone", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnResult login(@RequestParam(name = "telPhone") String telPhone,//
                              @RequestParam(name = "password") String password,//
                              HttpSession session) throws ResponseException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if (org.apache.commons.lang3.StringUtils.isEmpty(telPhone)) {
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录流程，校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telPhone, this.EncodeByMD5(password));
        //登录凭证加入到用户登录成功的session内,userModel信息放入到session
        session.setAttribute("IS_LOGIN", true);
        session.setAttribute("LOGIN_USER", userModel);

        return ReturnResult.create(null);
    }

    //用户注册接口
    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "telPhone", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "otpCode", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "age", value = "年龄", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "gender", value = "性别", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnResult register(@RequestParam(name = "telPhone") String telPhone,//
                                 @RequestParam(name = "otpCode") String otpCode,//
                                 @RequestParam(name = "name") String name,//
                                 @RequestParam(name = "age") Integer age,//
                                 @RequestParam(name = "gender") Integer gender,//
                                 @RequestParam(name = "password") String password,//
                                 HttpServletRequest httpServletRequest) throws ResponseException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //redis存的otpcode
        String inRedisOtp_Code = userService.getUserOtpCodeByRedis(telPhone);
        //session存的otpcode（ajax，session跨域的使用）
        httpServletRequest.getSession().getAttribute(telPhone);
        //redis的otpcode和页面传来的对比
        if (!StringUtils.equals(otpCode, inRedisOtp_Code)) {//使用alibaba的StringUtils
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR, "短信验证未通过");
        }
        //注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setTelphone(telPhone);
        userModel.setRegistereMode("byphone");
        //密码明文使用MD5加密
//        userModel.setEncrptPassword(MD5Encoder.encode(password.getBytes()));//java自带的MD5只支持16位的
        userModel.setEncrptPassword(this.EncodeByMD5(password));

        userService.register(userModel);
        return ReturnResult.create(null);
    }

    //手动设置密码加密算法
    public String EncodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    //用户获取otp短信接口
    @ApiOperation(value = "短信登陆接口", notes = "用户登录短信接口")
    @ApiImplicitParam(paramType = "query", name = "telPhone", value = "手机号码", required = true, dataType = "String")
    @RequestMapping(value = "/getOtp", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnResult getOtp(@RequestParam(name = "telPhone") String telPhone, HttpServletRequest httpServletRequest, HttpServletResponse resp) {
/*        //请求地址
        String url="http://106.ihuyi.cn/webservice/sms.php?method=Submit";
        //请求参数
        String account="C07536083";
        String password="a3c164c9dafbd27a1eb52e9161d0b276 ";*/
        //生成otp验证码，这里随机数生成
        Random random = new Random();
        //此时取[0-99999)额随机数
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String mobile_code = String.valueOf(randomInt);
       /* String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
        Map<String, String> params=new HashMap<String,String>();
        params.put("account", account);
        params.put("password", password);
        params.put("mobile", telPhone);
        params.put("content", content);
        Map<String, Object> json=new HashMap<String,Object>();
        //发送请求
        try {
            String xml= HttpClientUtil.doPost(url, params);
            //解析，这是xml字符串，不是xml文件
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            //得到xml字符串的信息
            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");
            //信息放在对象中

            json.put("code", code);//2表示成功
            json.put("msg", msg);
            json.put("smsid", smsid);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //otp验证码和用户手机号关联,这里使用HttpSession，其实最好的使用redis
        httpServletRequest.getSession().setAttribute(telPhone, mobile_code);
        //用户验证码与手机号码关联存在redis中，因为redis是key和value存储。重复获取会替换
        userService.userLoginInfoByRedis(telPhone, mobile_code);

        //otp验证码通过手机号发送，省略
        System.out.println("telPhone:" + telPhone + "&otpCode:" + mobile_code);

//        return ReturnResult.create(json);
        return ReturnResult.create(null);
    }

    @ApiOperation(value = "获得用户id", notes = "根据用户id获得用户数据")
    @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "Integer")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public ReturnResult getUser(@RequestParam(name = "id") Integer id) throws ResponseException {
        UserModel userModel = userService.getUserById(id);
        //模拟用户不存在
        if (userModel == null) {
            throw new ResponseException(EnumError.USER_NOT_EXISTS);
        }
        UserVo userVo = convertFromModel(userModel);
//        return TaotaoResult.ok(userVo);
        return ReturnResult.create(userVo);
    }

    //根据需求返回视图
    private UserVo convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel, userVo);
        return userVo;
    }

    //测试@PathVariable
    @ApiOperation(value = "测试restful", notes = "测试接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "模拟测试page", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "message", value = "模拟测试message", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "test", value = "模拟测试test", required = true, dataType = "String"),

    })
    @RequestMapping(value = "/{page}/{message}/{test}", method = RequestMethod.GET)
    @ResponseBody
    public ChrReturnResult mapp(@PathVariable String page, @PathVariable String message, @PathVariable String test) {
        System.err.println(page);
        System.err.println(message);
        System.err.println(test);
        return ChrReturnResult.create(page + "==" + message + "==" + test);
    }
}