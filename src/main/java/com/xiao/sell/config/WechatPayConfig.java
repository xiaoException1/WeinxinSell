package com.xiao.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatPayConfig {
    @Autowired
    private WechatAccountProperties wechatAccountProperties;

    @Bean
    public BestPayServiceImpl bestPayService(){
        BestPayServiceImpl payService = new BestPayServiceImpl();
        payService.setWxPayH5Config(wxPayH5Config());
        return  payService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountProperties.getMpAppId());
        wxPayH5Config.setAppSecret(wechatAccountProperties.getMpAppSecret());
        wxPayH5Config.setMchId(wechatAccountProperties.getMchId());
        wxPayH5Config.setMchKey(wechatAccountProperties.getMchKey());
        wxPayH5Config.setKeyPath(wechatAccountProperties.getKeyPath());
        wxPayH5Config.setNotifyUrl(wechatAccountProperties.getNotifyUrl());
        return  wxPayH5Config;
    }
}
