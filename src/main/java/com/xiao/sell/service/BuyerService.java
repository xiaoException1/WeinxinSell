package com.xiao.sell.service;

import com.xiao.sell.dto.OrderDTO;

public interface BuyerService {

    public OrderDTO findOrderOne(String openId,String orderId);
    public OrderDTO cancelOrder(String openId,String orderId);
}
