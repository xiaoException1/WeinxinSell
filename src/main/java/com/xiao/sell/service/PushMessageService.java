package com.xiao.sell.service;

import com.xiao.sell.dto.OrderDTO;

/**
 * 微信消息推送
 */
public interface PushMessageService {

    public void pushOrderStausMessage(OrderDTO orderDTO);
}
