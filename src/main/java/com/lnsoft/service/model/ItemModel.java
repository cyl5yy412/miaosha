package com.lnsoft.service.model;

/**
 * Created by chr on 2019/1/1/0001.
 */

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 拿到需求之后不要直接建表，学会构建领域模型的思维，先考虑xxxModel的属性，在设置表，考虑哪些字段需要设计优化等
 * 商品模型
 */
public class ItemModel {

    private Integer id;

    //商品名字
    @NotBlank(message = "商品名称不能为空")
    private String title;

    //商品价格
    @NotNull(message = "商品价格不能不写")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    //商品库存
    @NotNull(message = "库存不能不写")
    private Integer stock;

    //商品描述
    @NotBlank(message = "描述信息不能不写")
    private String description;

    //商品销量
    private Integer sales;

    //商品描述图片的url
    @NotBlank(message = "图片信息不能为空")
    private String imgUrl;
//---------------------------------------------------
    //使用聚合模型，就是嵌套
    /**
     * 如果promoModel 不为null，则表示其拥有还未结束的秒杀活动：还未结束代表（正在进行的）和（还未开始的）
     */
    private PromoModel promoModel;
//---------------------------------------------------


    public PromoModel getPromoModel() {
        return promoModel;
    }

    public void setPromoModel(PromoModel promoModel) {
        this.promoModel = promoModel;
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
