package com.xiao.sell.service.impl;

import com.xiao.sell.domain.SellerInfo;
import com.xiao.sell.repository.SellerInfoRepository;
import com.xiao.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
