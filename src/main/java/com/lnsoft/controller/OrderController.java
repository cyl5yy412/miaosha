package com.lnsoft.controller;

import com.lnsoft.response.ReturnResult;
import com.lnsoft.response.error.BaseController;
import com.lnsoft.response.error.EnumError;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.OrderService;
import com.lnsoft.service.model.OrderModel;
import com.lnsoft.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created By Chr on 2019/1/7/0007.
 */
@Controller
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    //封装下单请求
    @ResponseBody
    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public ReturnResult createOrder(@RequestParam("itemId") Integer itemId,//
                                    @RequestParam("amount") Integer amount,//
                                    @RequestParam(value = "promoId", required = false) Integer promoId,//
                                    HttpSession session) throws ResponseException {

        Boolean isLogin = (Boolean) session.getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            //此时用户未登录，也返回给前端json格式的错误码，和错误信息
            throw new ResponseException(EnumError.USER_NOT_LOGIN, "用户还没有登陆，暂时无法下单");
        }
        //获取用户的登陆信息：userId就是用户的登陆信息
        UserModel userModel = (UserModel) session.getAttribute("LOGIN_USER");

        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);

        return ReturnResult.create(null);
    }
}
