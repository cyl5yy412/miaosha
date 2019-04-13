package com.lnsoft.controller;

import com.lnsoft.response.ReturnResult;
import com.lnsoft.response.error.BaseController;
import com.lnsoft.response.error.EnumError;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.OrderService;
import com.lnsoft.service.model.OrderModel;
import com.lnsoft.service.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 订单接口
 * <p>
 * Created By Chr on 2019/1/7/0007.
 */
@Api(value = "订单接口", description = "该接口为下单接口")
@Controller
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    //封装下单请求2
    @ApiOperation(value = "根据商品判断是否为秒杀商品的下单", notes = "普通商品和秒杀活动商品的下单接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "itemId", value = "商品id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "amount", value = "商品数量", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "promoId", value = "秒杀商品id", required = true, dataType = "Integer"),
    })
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
