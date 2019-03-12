package com.lnsoft.service;

import com.lnsoft.response.ReturnResult;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.model.CartModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车
 * <p>
 * Created By Chr on 2019/3/11/0011.
 */
public interface CartService {
    /**
     * 添加购物车:先查看有没有购物车：
     * 没有，有创建购物车;
     * 有，查看该商品是否在购物车里：
     * 在，叠加；
     * 不在，添加进购物车
     *
     * @param id       商品id
     * @param num      商品数量* @param request
     * @param request
     * @param response
     * @return
     * @throws ResponseException
     */
    ReturnResult addCart(Integer id, Integer num, HttpServletRequest request, HttpServletResponse response) throws ResponseException;


    /**
     * 获得购物车的内容
     *
     * @return
     */
    List<CartModel> getCartModelList(HttpServletRequest request);

    /**
     * 更新购物车的内容：修改商品数量
     *
     * @param response
     * @param request
     * @param itemId   商品id
     * @param num      更新之后的商品的数量
     * @return
     */
    ReturnResult updateCartModelList(HttpServletResponse response, HttpServletRequest request, Integer itemId, Integer num);

    /**
     * 删除购物车的某个商品
     *
     * @param response
     * @param request
     * @param itemId
     */
    ReturnResult deleteCartModel(HttpServletResponse response, HttpServletRequest request, Integer itemId);
}
