package com.lnsoft.controller;

import com.lnsoft.response.ReturnResult;
import com.lnsoft.response.error.BaseController;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.CartService;
import com.lnsoft.service.model.CartModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车接口
 * <p>
 * Created By Chr on 2019/3/11/0011.
 */
@Api(value = "购物车接口", description = "购物车：添加购物车，查看购物车商品，更新购物车商品，删除商品")
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {


    @Resource
    private CartService cartService;

    @ApiOperation(value = "购物车新增商品", notes = "添加购物车接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "itemId", value = "商品id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "num", value = "商品数量", required = true, dataType = "Integer"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult addCart(@RequestParam(value = "itemId", required = true) Integer itemId,//
                                @RequestParam(value = "num", required = true) Integer num,//
                                HttpServletRequest request, HttpServletResponse response) throws ResponseException {
        return cartService.addCart(itemId, num, request, response);
    }


    @ApiOperation(value = "查看购物车", notes = "查看购物车的商品")
    @ResponseBody
    @RequestMapping(value = "/cartInfo", method = RequestMethod.GET)
    public ReturnResult cartInfo(HttpServletRequest request) {

        List<CartModel> cartModelList = cartService.getCartModelList(request);
        return ReturnResult.create(cartModelList);
    }

    @ApiOperation(value = "添加购物车", notes = "添加商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "itemId", value = "商品id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "num", value = "商品数量", required = true, dataType = "Integer"),
    })
    @ResponseBody
    @RequestMapping(value = "/update/{itemId}/{num}", method = RequestMethod.POST)
    public ReturnResult cartUpdate(@PathVariable(value = "itemId", required = true) Integer itemId,//
                                   @PathVariable(value = "num", required = true) Integer num,//
                                   HttpServletRequest request, HttpServletResponse response) {

        return cartService.updateCartModelList(response, request, itemId, num);
    }

    @ApiOperation(value = "删除商品", notes = "删除购物车的商品")
    @ApiImplicitParam(paramType = "path", name = "itemId", value = "商品id", required = true, dataType = "Integer")
    @ResponseBody
    @RequestMapping(value = "/delete/{itemId}", method = RequestMethod.POST)
    public ReturnResult cartDelete(@PathVariable(value = "itemId", required = true) Integer itemId,//
                                   HttpServletRequest request, HttpServletResponse response) {

        return cartService.deleteCartModel(response, request, itemId);
    }
}
