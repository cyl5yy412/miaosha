package com.lnsoft.service.impl;

import com.lnsoft.dataobject.PromoInfoDo;
import com.lnsoft.mapper.PromoInfoDoMapper;
import com.lnsoft.service.PromoService;
import com.lnsoft.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created By Chr on 2019/1/9/0009.
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoInfoDoMapper promoInfoDoMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoInfoDo promoInfoDo = promoInfoDoMapper.selectByItemId(itemId);
        //dataobject->model
        PromoModel promoModel = convertFromDataObject(promoInfoDo);
        if (promoModel == null) {
            return null;
        }
        //秒杀活动状态：1没开始。2进行中。3已结束
        //判断当前时间是否秒杀活动即将开始 或 正在进行
        if (promoModel.getStartTime().isAfterNow()) {//活动开始时间 在 当前时间之后,还未开始
            promoModel.setStatus(1);
        } else if (promoModel.getEndTime().isBeforeNow()) {//活动结束时间在 当前时间之前,已结束
            promoModel.setStatus(3);
        } else {//活动正在进行，还未结束
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    //测试
    public static void main(String args[]) {
        DateTime now = new DateTime(2019, 1, 9, 19, 39);
        System.out.println(now);
        boolean afterNow = now.isBeforeNow();
        System.out.println(afterNow);
    }

    //dataobject->model
    private PromoModel convertFromDataObject(PromoInfoDo promoInfoDo) {
        if (promoInfoDo == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoInfoDo, promoModel);
        //数据库的价格是double，实体类是Double，model是BigDecimal类型
        promoModel.setPromoItemPrice(new BigDecimal(promoInfoDo.getPromoItemPrice()));
        //实体类是util的date，model是joda的datetiem非常强大，包装了时间的全部实现
        promoModel.setStartTime(new DateTime(promoInfoDo.getStartTime()));
        promoModel.setEndTime(new DateTime(promoInfoDo.getEndTime()));
        return promoModel;
    }
}
