package com.xiao.sell.service.impl;

import com.xiao.sell.dto.OrderDTO;
import com.xiao.sell.enums.ResultEnum;
import com.xiao.sell.exception.SellException;
import com.xiao.sell.service.BuyerService;
import com.xiao.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openId, String orderId) {
        return  checkOrderOwner(openId,orderId);
    }

    private OrderDTO checkOrderOwner(String openId, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO==null){
            return  null;
        }
        if (!orderDTO.getBuyerOpenid().equals(orderId)){
            log.error("【查询订单】订单的openid不一致，openid={},orderid={}",openId,orderId);
            throw  new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openId, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openId, orderId);
        if (orderDTO==null){
            log.error("【取消订单】查不到订单，orderid={},openid={}",orderId,openId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        return orderDTO;
    }
}
