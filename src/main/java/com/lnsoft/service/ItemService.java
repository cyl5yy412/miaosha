package com.lnsoft.service;

import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.model.ItemModel;

import java.util.List;

/**
 * Created by chr on 2019/1/1/0001.
 */
public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel) throws ResponseException;

    //商品列表
    List<ItemModel> itemList();

    //商品详情
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws ResponseException;

    //销量增加
    void increaseSales(Integer itemId, Integer amount) throws ResponseException;
}
