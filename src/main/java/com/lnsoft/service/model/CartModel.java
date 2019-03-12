package com.lnsoft.service.model;

import java.math.BigDecimal;

/**
 * 购物车领域模型
 * <p>
 * Created By Chr on 2019/3/11/0011.
 */
public class CartModel {
    //购物车id
    private String cartId;


    //购物车有商品
    private ItemModel itemModel;

    //商品数量
    private Integer number;

    //总价
    private BigDecimal priceSum;


    //=========================
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public ItemModel getItemModel() {
        return itemModel;
    }

    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }
}
