package com.xiao.sell.service.impl;

import com.xiao.sell.config.WechatAccountProperties;
import com.xiao.sell.dto.OrderDTO;
import com.xiao.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PushMessageServiceImp implements PushMessageService {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountProperties wechatAccountProperties;

    @Override
    public void pushOrderStausMessage(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(wechatAccountProperties.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(orderDTO.getBuyerOpenid());

        List<WxMpTemplateData> templateData = new ArrayList<>();
        templateData.add(new WxMpTemplateData( "first", "亲，请记得收货。"));
        templateData.add( new WxMpTemplateData("keyword1", "微信点餐")) ;
        templateData.add( new WxMpTemplateData("keyword2",  orderDTO.getOrderId()));
        templateData.add( new WxMpTemplateData("keyword3", orderDTO.getOrderStatusEnum().getMessage()));
        templateData.add( new WxMpTemplateData("keyword4", "￥"+orderDTO.getOrderAmount()));
        templateData.add( new WxMpTemplateData("remark", "欢迎再次光临"));

        templateMessage.setData(templateData);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
           // e.printStackTrace();
            log.error("【微信模版消息】发送失败, {}", e);

        }
    }
}
