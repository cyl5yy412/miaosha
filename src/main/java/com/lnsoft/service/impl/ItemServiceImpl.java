package com.lnsoft.service.impl;

import com.lnsoft.dataobject.ItemInfoDO;
import com.lnsoft.dataobject.ItemStockDO;
import com.lnsoft.mapper.ItemInfoDOMapper;
import com.lnsoft.mapper.ItemStockDOMapper;
import com.lnsoft.response.error.EnumError;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.ItemService;
import com.lnsoft.service.PromoService;
import com.lnsoft.service.model.ItemModel;
import com.lnsoft.service.model.PromoModel;
import com.lnsoft.validator.ValidationResult;
import com.lnsoft.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chr on 2019/1/1/0001.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemInfoDOMapper itemInfoDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private PromoService promoService;

    /**
     * 面向领域设计习惯：1.校验入参2.转化itemModel为dataobject3.写入数据库4.返回创建完成的对象
     * 领域模型，返回实体，为了让上游知道创建的对象的状态
     *
     * @param itemModel
     * @return
     */
    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws ResponseException {
        //validate,校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrorMessage());
        }

        //转化itemModel为dataobject
        ItemInfoDO itemInfoDO = this.convertItemInfoDoFromModel(itemModel);
        //写入数据库
        itemInfoDOMapper.insertSelective(itemInfoDO);
        /**
         * 为什么要用这一步？因为xml设置了useGeneratedKeys="true" keyProperty="id"
         * itemInfoDo的id属性就有了值，但是Model的id属性没有，赋值给model的id，传递给itemStock的id
         */
        itemModel.setId(itemInfoDO.getId());
        ItemStockDO itemStockDO = this.convertItemStockDoFromModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    //model->dataobject
    private ItemStockDO convertItemStockDoFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        //这两个方法都行
//        itemStockDO.setItemId(itemModel.getId());
//        BeanUtils.copyProperties(itemModel, itemStockDO);
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    //model->dataobject
    private ItemInfoDO convertItemInfoDoFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemInfoDO itemInfoDO = new ItemInfoDO();
        BeanUtils.copyProperties(itemModel, itemInfoDO);//model的price是bigdecimal，实体类是double，copy不copy类型不一样的字段
        /**
         * bigdecimal转为double存在数据库，因为double被json后1.9可能被转为1.9999，存在自损风险
         * 存的话数据库为double，存在数据库的为double类型，返回前端就返回bigdecimal
         */
        //bigdecimal->double
        itemInfoDO.setPrice(itemModel.getPrice().doubleValue());
        return itemInfoDO;
    }

    @Override
    public List<ItemModel> itemList() {
        List<ItemInfoDO> itemInfoDOList = itemInfoDOMapper.itemList();
        /**
         * 使用jdk8的stream的API的map方法：
         * 将ItemInfoDo的每个属性map成ItemModel
         * 跟Lambda表达式差不多，itemInfoDo就是里面的各个属性的值，只要的到对应的itemStockDo的各个属性的值
         * 使用convert方法就可以得到ItemModel
         */
        //3.使用List接收
        List<ItemModel> itemModelList = itemInfoDOList.stream().map(itemInfoDO -> {
            //1.根据商品的id得到对应的每一个stock表中的信息
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemInfoDO.getId());
            ItemModel itemModel = convertModelFromDataObject(itemInfoDO, itemStockDO);
            return itemModel;
            //2.把该方法返回一个List几个
        }).collect(Collectors.toList());
        //最后返回
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemInfoDO itemInfoDO = itemInfoDOMapper.selectByPrimaryKey(id);
        if (itemInfoDO == null) {
            return null;
        }
        //操作获得库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemInfoDO.getId());
        //dataobject->model
        ItemModel itemModel = this.convertModelFromDataObject(itemInfoDO, itemStockDO);
//-------------------秒杀--------------------------------
        //获取活动商品信息，看看是否是秒杀商品
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        //当 商品的promoModel不为null代表 该商品有秒杀活动，status不为3代表未开始或进行中
        if (promoModel != null && promoModel.getStatus() != 3) {
            //如果是未开始，或者进行中，设置在itemModel的模型为promoModel
            itemModel.setPromoModel(promoModel);
        }
//---------------------------------------------------
        return itemModel;
    }

    @Transactional
    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) throws ResponseException {
        //影响的条目数
        int affectedRow = itemStockDOMapper.decreaseStock(itemId, amount);

        if (affectedRow > 0) {
            //更新库存成功
            return true;
        } else {
            //更新库存失败
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws ResponseException {
        itemInfoDOMapper.increaseSales(itemId, amount);
    }

    //dataobject->model
    private ItemModel convertModelFromDataObject(ItemInfoDO itemInfoDO, ItemStockDO itemStockDO) {
        if (itemInfoDO == null || itemStockDO == null) {
            return null;
        }
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemInfoDO, itemModel);
        //double->bigdecimal:数据库查出来的是double，set进bigdecimal
        itemModel.setPrice(new BigDecimal(itemInfoDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}
