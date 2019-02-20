package com.lnsoft.service.impl;

import com.lnsoft.dataobject.OrderInfoDo;
import com.lnsoft.dataobject.SequenceInfoDo;
import com.lnsoft.mapper.OrderInfoDoMapper;
import com.lnsoft.mapper.SequenceInfoDoMapper;
import com.lnsoft.response.error.EnumError;
import com.lnsoft.response.error.ResponseException;
import com.lnsoft.service.ItemService;
import com.lnsoft.service.OrderService;
import com.lnsoft.service.UserService;
import com.lnsoft.service.model.ItemModel;
import com.lnsoft.service.model.OrderModel;
import com.lnsoft.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created By Chr on 2019/1/7/0007.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderInfoDoMapper orderInfoDoMapper;
    //该mapper为了模拟oracle的序列
    @Autowired
    private SequenceInfoDoMapper sequenceInfoDoMapper;

    @Transactional
    @Override
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws ResponseException {
        //1.校验下单状态，下单的商品是否存在，用户是否合法，购买数量是否正确，落单之前最基本的校验
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }
        if (amount <= 0 || amount > 99) {
            throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR, "数量信息不存在");
        }
        //---------------秒杀--------------
        //校验活动，通过验证promoId是不是null判断：为null-普通商品，不是null-秒杀商品
        if (promoId != null) {
            //(1)校验对应活动是否存在这个 适用的商品
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
                //(2)校验活动是否正在进行
            } else if (itemModel.getPromoModel().getStatus().intValue() != 2) {
                throw new ResponseException(EnumError.PARAMETER_VALIDATION_ERROR, "活动还未开始");
            }
        }
        //---------------秒杀--------------
        /**
         * 落单减库存：只要订单下单成功createOrder()调用之前，就会锁定给该用户使用，
         * 也就是锁定给这个订单使用，如果锁库存失败，就下单失败，如果锁库存成功，必定下单成功，比较有保障，靠谱
         */
        /**
         * 支付减库存：落单时候看一下库存，如果有库存，不去锁，返回有库存，等到支付成功之后才真正扣减库存，
         * 但是该方法无法保证支付成功之后库存是否还有，无法保证是否超卖了商家的商品，
         * 这种方法多用于商家有备用库存，迫使买家及时付款
         */
        //2.这里使用落单减库存的方法
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new ResponseException(EnumError.STOCK_NOT_ENOUGH);
        }
        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        //---------------秒杀--------------
        if(promoId!=null){
            //秒杀商品单价
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            //普通商品单价
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        //---------------秒杀--------------
        //注意BigDecimal的类型怎么转换的
//        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        //---------------秒杀--------------秒杀的总价格也需要改变
        //总金额：单价*数量
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        //---------------秒杀--------------秒杀的总价格也需要改变
        //生成交易流水号，就是订单号
        orderModel.setId(this.generateOrderNumber());
        //订单信息保存数据库
        OrderInfoDo orderInfoDo = this.convertFromOrderModel(orderModel);
        orderInfoDoMapper.insertSelective(orderInfoDo);
        //下单成功，增加销量
        itemService.increaseSales(itemId, amount);
        //4.返回前端
        return orderModel;
    }

    //测试
    public static void main(String args[]){
        BigDecimal price=new BigDecimal(20);
        Integer amount=2;
        BigDecimal decimal = price.multiply(new BigDecimal(amount));//价格*数量
        System.out.println(decimal);//40,结果相乘
    }

    //自定义的流水号
    //当createOrder新增失败回滚之后，新增失败的sequence的值不应该回滚，不应该导致下一个事务拿到了上一次失败的新增事务的sequence的值，
    //为了保证sequence值全局唯一性，使用REQUIRES_NEW，永远创建一个新的失误
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNumber() {
        //订单号16位：
        StringBuilder stringBuilder = new StringBuilder();
        //前八位为年月日，作用，如果是数据量过大，可以根据前八位归档信息
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);
        //中间6位为自增序列，在一天内的交易订单位数最大是六位，视情况而定
        //获取当前sequence,从mysql中拿取
        SequenceInfoDo sequenceInfoDo = sequenceInfoDoMapper.getSequenceByName("order_info");
        int sequence = sequenceInfoDo.getCurrentValue();
        //判断是否是最大位数值999999，是就重置当前值为初始值
        if (sequence >= sequenceInfoDo.getMaxValue()) {
            sequenceInfoDo.setCurrentValue(sequenceInfoDo.getInitValue());
            sequenceInfoDoMapper.updateByPrimaryKeySelective(sequenceInfoDo);
        }
        //得到sequence当前的值之后，把下一个sequence的current的值算出来：加上步长，添加到数据库
        sequenceInfoDo.setCurrentValue(sequenceInfoDo.getCurrentValue() + sequenceInfoDo.getStep());
        sequenceInfoDoMapper.updateByPrimaryKeySelective(sequenceInfoDo);
        //拼接sequence
        String sequenceStr = String.valueOf(sequence);
        for (int x = 0; x < 6 - sequenceStr.length(); x++) {
            //不足的用0填充
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        //最后两位为分库分表位，00-99，订单的水平拆分，将订单拆分到100个库对应的100个表里，分散数据库的查询和落单压力
        stringBuilder.append("00");//暂时写死
        /**
         * 伪代码：扩展信息
         * 比如userId=1000222
         * userId % 100 就作为水平拆分的依据，那么这个用户的订单信息永远都在固定的那一个库的那一个表中，这样就是分库分表的路由信息
         */
        return stringBuilder.toString();
    }

    //model->dataObject
    private OrderInfoDo convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderInfoDo orderInfoDo = new OrderInfoDo();
        BeanUtils.copyProperties(orderModel, orderInfoDo);
        //Model的价格是BigDecimal，而实体类的是Double类型的，需要手动赋值
        orderInfoDo.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderInfoDo.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderInfoDo;
    }
}
