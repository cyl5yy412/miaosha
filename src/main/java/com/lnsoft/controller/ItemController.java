package com.lnsoft.controller;

import com.lnsoft.controller.viewobject.ItemVo;
import com.lnsoft.response.ReturnResult;
import com.lnsoft.response.error.BaseController;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.ItemService;
import com.lnsoft.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Created by chr on 2019/1/1/0001.
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    //测试spring提供的替补机制，返回Callable方式
    @RequestMapping("/testC")
    @ResponseBody
    public Callable<ReturnResult> testC() {
        Callable<ReturnResult> callable = new Callable<ReturnResult>() {
            @Override
            public ReturnResult call() throws Exception {
                List<ItemModel> itemModelList = itemService.itemList();
                List<ItemVo> itemVoList = itemModelList.stream().map(itemModel -> {
                    ItemVo itemVo = convertVoFromModel(itemModel);
                    return itemVo;
                }).collect(Collectors.toList());
                return ReturnResult.create(itemVoList);
            }
        };
        return callable;
    }

    //商品列表页
    @RequestMapping(value = "itemList", method = RequestMethod.GET)
    @ResponseBody
    public ReturnResult itemList() {
        List<ItemModel> itemModelList = itemService.itemList();
        //*******************************************************
        //使用stream的API将itemModel的各个属性转化为ItemVo
        List<ItemVo> itemVoList = itemModelList.stream().map(itemModel -> {
            ItemVo itemVo = this.convertVoFromModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
        //*******************************************************
        return ReturnResult.create(itemVoList);
    }

    //商品详情页的浏览
    @RequestMapping(value = "getItem", method = RequestMethod.GET)
    @ResponseBody
    public ReturnResult getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVo itemVo = convertVoFromModel(itemModel);
        return ReturnResult.create(itemVo);
    }

    //创建商品
    @RequestMapping(value = "createItem", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult createItem(@RequestParam(name = "title") String title,//
                                   @RequestParam(name = "price") BigDecimal price,//
                                   @RequestParam(name = "description") String description,//
                                   @RequestParam(name = "stock") Integer stock,//
                                   @RequestParam(name = "imgUrl") String imgUrl) throws ResponseException {

        //封装service 请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelReturn = itemService.createItem(itemModel);
        ItemVo itemVo = this.convertVoFromModel(itemModelReturn);

        return ReturnResult.create(itemVo);
    }

    private ItemVo convertVoFromModel(ItemModel itemModel) {

        if (itemModel == null) {
            return null;
        }
        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel, itemVo);
        //---------------------------------------------------
        if (itemModel.getPromoModel() != null) {
            //有正在进行 或 即将进行的 秒杀活动,这里把itemVo新加的属性通过itemModel聚合的PromoModel属性，赋值进去
            itemVo.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVo.setPromoId(itemModel.getPromoModel().getId());
//            itemVo.setStartTime(itemModel.getPromoModel().getStartTime());
            //这里也改用jodadatetime的的Format方法转换时间格式
            itemVo.setStartTime(itemModel.getPromoModel().getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setEndTime(itemModel.getPromoModel().getEndTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {//没有秒杀活动
            itemVo.setPromoStatus(0);
        }
        //---------------------------------------------------
        return itemVo;
    }


    //测试java 8 的stream的map的方法和lambda
    public List<ItemVo> test() {
        List<ItemModel> itemModelList = new ArrayList<>();
        ItemModel itemModel1 = new ItemModel();
        ItemModel itemModel2 = new ItemModel();

        itemModel1.setId(1);
        itemModel1.setTitle("22");
        itemModel2.setId(2);
        itemModel2.setTitle("33");
        itemModelList.add(itemModel1);
        itemModelList.add(itemModel2);

        List<ItemVo> itemVoList = itemModelList.stream().map(itemModel -> {
            ItemVo itemVo = this.convertVoFromModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
        return itemVoList;
    }

    public static void main(String args[]) {
        List<ItemVo> itemVoList = new ItemController().test();
        itemVoList.forEach(itemVo -> {
            System.out.println(itemVo.getId() + ":" + itemVo.getTitle() + "====");
        });
    }
}
