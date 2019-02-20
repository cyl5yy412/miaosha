package com.lnsoft.service;

import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.model.OrderModel;

/**
 * Created By Chr on 2019/1/7/0007.
 */
public interface OrderService {

    //创建订单
    /**
     * 有两种设计方式：
     *      1.前端url上传过来秒杀活动id，然后下单接口内校验 对应id是否属于 对应商品且活动已经开始（必须需要校验）
     *          如果前端传送过来，就去校验，是否是秒杀商品
     *          如果前端不传送，就认为不是秒杀商品
     *      2.直接在下单接口内，判断对应的商品 是否存在秒杀活动，若存在进行中的则以秒杀价格下单
     *          用户下单之后，判断订单的类型，秒杀类型或者是普通类型
     *
     * 两种方式比较：
     *     两种方案各有优点，但是第一种优势更大，原因：
     *      （1）可扩展性更大
     *      第一种方式可以根据url判断用户是以什么方式进入的下单页面，因为一个商品同一时间可能存在多个秒杀活动内，
     *      通过前段的使用路径，判断用户使用不同app进入秒杀同一款商品
     *      （2）
     *      如果在订单结果内判断是否秒杀商品下单，则普通不是秒杀商品的也需要判断，性能比较伤
     */
    //这里使用第一种
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws ResponseException;

}
