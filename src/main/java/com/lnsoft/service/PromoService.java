package com.lnsoft.service;

import com.lnsoft.service.model.PromoModel;

/**
 * Created By Chr on 2019/1/9/0009.
 */
public interface PromoService {

    //根据当前的商品itemId查询出当前商品的(当前商品包括：即将进行 或者 正在进行 秒杀活动的商品)：秒杀信息，和商品信息
    PromoModel getPromoByItemId(Integer itemId);
}
