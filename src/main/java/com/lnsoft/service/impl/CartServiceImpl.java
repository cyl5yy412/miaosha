package com.lnsoft.service.impl;

import com.alibaba.fastjson.JSON;
import com.lnsoft.response.ReturnResult;
import com.lnsoft.response.error.EnumError;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.CartService;
import com.lnsoft.service.ItemService;
import com.lnsoft.service.model.CartModel;
import com.lnsoft.service.model.ItemModel;
import com.lnsoft.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created By Chr on 2019/3/11/0011.
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("43200")//5day
    private int COOKIE_EXP;

    @Resource
    private ItemService itemService;

    @Override
    public ReturnResult addCart(Integer itemId, Integer num, HttpServletRequest request, HttpServletResponse response) throws ResponseException {

        //购物车
        List<CartModel> cartList = getCartList(request);
        //商品
        ItemModel itemModel = itemService.getItemById(itemId);

        if (itemModel == null) {
            throw new ResponseException(EnumError.UNKNOW_ERROR, "没有商品");
        }
        //有购物车或无购物车
        if (cartList == null) {//没购物车,new 车，商品放入车里，车放在cookie里
            cartList = new ArrayList<>();
            CartModel cartModel = new CartModel();
            cartModel.setCartId(UUID.randomUUID().toString());
            cartModel.setNumber(num);
            cartModel.setItemModel(itemModel);
            cartList.add(cartModel);
        } else {//有购物车，把新来的商品更新在cookie里
            //车里有没有该商品:遍历
            boolean haveFlag = false;
            for (CartModel cartModel :
                    cartList) {
                //这一Integer为对象，包装类型，会比对地址，需要转换为基本类型才能比对
                if (cartModel.getItemModel().getId().intValue() == itemId.intValue()) {//有该商品，数量+num
                    cartModel.setCartId(UUID.randomUUID().toString());
                    cartModel.setNumber(num + cartModel.getNumber());
                    cartModel.setItemModel(itemModel);
                    cartList.add(cartModel);
                    haveFlag = true;
                    break;
                }
            }
            //车没有该商品,商品放入购物车
            if (!haveFlag) {
                CartModel cartModel = new CartModel();
                cartModel.setCartId(UUID.randomUUID().toString());
                cartModel.setNumber(num);
                cartModel.setItemModel(itemModel);
                cartList.add(cartModel);
            }
        }
        //把购物车商品列表写入cookie
        CookieUtils.setCookie(request, response, "TT_CART7", JSON.toJSONString(cartList), COOKIE_EXP, true);
//        CookieUtils.setCookie(request, response, "TT_CART", JSON.toJSONString(cartList));
        //返回ReturnResult
        return ReturnResult.create(null);

//===================================================================================//

        /*
//                1、接收商品id

//        2、从cookie中查看购物车商品列表：有车或无车
        List<CartModel> cartList = getCartList(request);
//        3、从商品列表中查询列表是否存在此商品
        boolean haveFlag = false;
        if (cartList != null) {
            for (CartModel cartModel :
                    cartList) {
                //商品已经在购物车存在
                if (cartModel.getItemModel().getId() == itemId) {
                    //        4、如果存在商品的数量加上参数中的商品数量
                    cartModel.setNumber(num + cartModel.getNumber());
                    haveFlag = true;
                    break;
                }
            }

//        5、如果不存在，调用rest服务，根据商品id获得商品数据。
            if (!haveFlag) {
                ItemModel itemModel = itemService.getItemById(itemId);
                if (itemModel == null) {
                    throw new ResponseException(EnumError.UNKNOW_ERROR, "商品不存在");
                } else {
                    CartModel cartModel = new CartModel();
                    cartModel.setCartId(UUID.randomUUID().toString());
                    cartModel.setNumber(num);
                    cartModel.setItemModel(itemModel);
                    //        6、把商品数据添加到cart列表中
                    cartList.add(cartModel);
                }
            }
        }
        //        7、把购物车商品列表写入cookie
        CookieUtils.setCookie(request, response, "TT_CART", JSON.toJSONString(cartList), COOKIE_EXP, true);
//        8、返回ReturnResult
        return ReturnResult.create(null);
*/

    }

    @Override
    public List<CartModel> getCartModelList(HttpServletRequest request) {

        return getCartList(request);
    }

    @Override
    public ReturnResult updateCartModelList(HttpServletResponse response, HttpServletRequest request, Integer itemId, Integer num) {
//        1、把购物车商品列表从cookie中取出来
//        2、根据itemId找到对应的商品
//        3、使用参数中的数量更新数量。
//        4、需要把购物车商品列表写回cookie。
//        5、返回json数据

        List<CartModel> cartList = getCartList(request);
        for (CartModel cartModel :
                cartList) {
            //寻找修改数量的商品
            if (cartModel.getItemModel().getId().intValue() == itemId.intValue()) {
                //修改数量
                cartModel.setNumber(num);
                break;
            }
        }
        //写回cookie
        CookieUtils.setCookie(request, response, "TT_CART7", JSON.toJSONString(cartList), COOKIE_EXP, true);
        return ReturnResult.create(null);
    }

    @Override
    public ReturnResult deleteCartModel(HttpServletResponse response, HttpServletRequest request, Integer itemId) {
//        1、接收商品id
//        2、从cookie中取购物车商品列表
        List<CartModel> cartList = getCartList(request);
//        3、找到对应id的商品
        for (CartModel cartModel :
                cartList) {
            if (cartModel.getItemModel().getId().intValue() == itemId.intValue()) {
                //        4、删除商品。
                cartList.remove(cartModel);
                break;
            }
        }

//        5、再重新把商品列表写入cookie。
        CookieUtils.setCookie(request, response, "TT_CART7", JSON.toJSONString(cartList), COOKIE_EXP, true);
//        6、返回成功
        return ReturnResult.create(null);
    }

    /**
     * 取购物车列表
     *
     * @param request
     * @return
     */
    private List<CartModel> getCartList(HttpServletRequest request) {

        String json = CookieUtils.getCookieValue(request, "TT_CART7", true);

        if (StringUtils.isBlank(json)) {
            return null;
        }
        System.out.println(json + "===========");
        List<CartModel> cartList = JSON.parseArray(json, CartModel.class);
        return cartList;
    }
}
