package com.xiao.sell.controller;

import com.xiao.sell.config.ProjectUrlConfig;
import com.xiao.sell.constants.CookieConstant;
import com.xiao.sell.constants.RedisConstant;
import com.xiao.sell.domain.SellerInfo;
import com.xiao.sell.enums.ResultEnum;
import com.xiao.sell.service.SellerService;
import com.xiao.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map){
        //1. openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", projectUrlConfig.getSell()+"/seller/order/list");
            return new ModelAndView("common/error");
        }
        String token_id= UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.PREFIX_TOKEN,token_id),openid,RedisConstant.EXPIRE, TimeUnit.SECONDS);
        CookieUtil.set(response, CookieConstant.TOKEN,token_id,CookieConstant.EXPIRE);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/seller/order/list");
    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.PREFIX_TOKEN,cookie.getValue()));
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url",  projectUrlConfig.getSell()+"/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
