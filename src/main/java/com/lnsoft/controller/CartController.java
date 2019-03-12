package com.lnsoft.controller;

import com.lnsoft.response.ReturnResult;
import com.lnsoft.response.error.BaseController;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.CartService;
import com.lnsoft.service.model.CartModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created By Chr on 2019/3/11/0011.
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {


    @Resource
    private CartService cartService;

    @RequestMapping("/add")
    @ResponseBody
    public ReturnResult addCart(@RequestParam(value = "itemId", required = true) Integer itemId,//
                                @RequestParam(value = "num", required = true) Integer num,//
                                HttpServletRequest request, HttpServletResponse response) throws ResponseException {
        return cartService.addCart(itemId, num, request, response);
    }

    @ResponseBody
    @RequestMapping("/cartInfo")
    public ReturnResult cartInfo(HttpServletRequest request) {

        List<CartModel> cartModelList = cartService.getCartModelList(request);
        return ReturnResult.create(cartModelList);
    }

    @ResponseBody
    @RequestMapping("/update/{itemId}/{num}")
    public ReturnResult cartUpdate(@PathVariable(value = "itemId", required = true) Integer itemId,//
                                   @PathVariable(value = "num", required = true) Integer num,//
                                   HttpServletRequest request, HttpServletResponse response) {

        return cartService.updateCartModelList(response, request, itemId, num);
    }

    @ResponseBody
    @RequestMapping("/delete/{itemId}")
    public ReturnResult cartDelete(@PathVariable(value = "itemId", required = true) Integer itemId,//
                                   HttpServletRequest request, HttpServletResponse response) {

        return cartService.deleteCartModel(response, request, itemId);
    }
}
