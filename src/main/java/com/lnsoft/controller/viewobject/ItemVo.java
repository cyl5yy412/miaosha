package com.lnsoft.controller.viewobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by chr on 2019/1/1/0001.
 */

/**
 * Vo只返回前端需要暴露的字段
 */
public class ItemVo{

    private Integer id;
    //商品名字
    private String title;
    //商品价格
    private BigDecimal price;
    //商品库存
    private Integer stock;
    //商品描述
    private String description;
    //商品销量
    private Integer sales;
    //商品描述图片的url
    private String imgUrl;

//---------------秒杀------------------------------------
    //记录商品是否在秒杀活动中，以及记录对应的状态：0没有秒杀活动。1秒杀活动没开始。2秒杀活动正在进行。3已结束
    private Integer promoStatus;
    //秒杀活动价格
    private BigDecimal promoPrice;
    //秒杀活动ID
    private Integer promoId;
    //秒杀活动的开始时间,用来作倒计时展示
//    private DateTime startTime;
    //这里把DateTime的时间格式转化为String，因为Jodadatetime的格式在前端显示有很多其他格式的时间，导致时间显示不出来
    private String startTime;
    private String endTime;
//---------------------------------------------------


    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

//    public DateTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(DateTime startTime) {
//        this.startTime = startTime;
//    }


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
