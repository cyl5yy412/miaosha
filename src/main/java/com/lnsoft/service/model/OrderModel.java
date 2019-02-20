package com.lnsoft.service.model;

import java.math.BigDecimal;

/**
 * Created By Chr on 2019/1/7/0007.
 */
//用户下单的交易模型

/**
 * 还是先构建领域模型，分析，在建表
 */
public class OrderModel {

    //交易号2018102100012828
    private String id;
    //购买的用户的id
    private Integer userId;
    //购买商品id
    private Integer itemId;

    //购买商品的单价,若promoId非空，则表示是以秒杀商品的价格
    private BigDecimal itemPrice;

    //购买数量
    private Integer amount;
    //购买金额,若promoId非空，则表示是以秒杀商品的价格
    private BigDecimal orderPrice;


//------------秒杀1-------------
    //若非空，则表示是以秒杀商品的方式下单
    private Integer promoId;

//------------秒杀1-------------
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}
