package com.xiao.sell.aspect;

import com.xiao.sell.constants.CookieConstant;
import com.xiao.sell.constants.RedisConstant;
import com.xiao.sell.exception.SellerAuthorizeException;
import com.xiao.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

   /* @Pointcut("execution(public * com.xiao.sell.controller.Seller*.*(..))"+
    "&& !execution(public * com.xiao.sell.controller.SellerUserController.*(..))")
    public void verify(){
      //  log.info("参数={},name={}",joinPoint.getArgs().toString(),joinPoint.getSignature().getName());
    }

    @Before("verify()")
    public void beforeDoVerify(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       HttpServletRequest request = requestAttributes.getRequest();
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
    }*/

    /**
     * 在切面切中方法之前执行
     * @param joinPoint
     */
   /*@Before("execution(public * com.xiao.sell.controller.Seller*.*(..))"+
           "&& !execution(public * com.xiao.sell.controller.SellerUserController.*(..))")*/
   public void verify(JoinPoint joinPoint){
         log.info("参数={},name={}",joinPoint.getArgs(),joinPoint.getSignature().getName());
       ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       HttpServletRequest request = requestAttributes.getRequest();
       Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
       if (cookie==null){
           log.warn("【登录校验】Cookie中查不到token");
           throw new SellerAuthorizeException();
       }
       String openkid = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.PREFIX_TOKEN,cookie.getValue()));
       if (StringUtils.isEmpty(openkid)) {
           log.warn("【登录校验】Redis中查不到openid");
           throw new SellerAuthorizeException();
       }
   }

}
