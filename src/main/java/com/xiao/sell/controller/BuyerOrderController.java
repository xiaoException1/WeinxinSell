package com.xiao.sell.controller;

import com.xiao.sell.VO.ResultVO;
import com.xiao.sell.converter.OrderForm2OrderDTOConverter;
import com.xiao.sell.dto.OrderDTO;
import com.xiao.sell.enums.ResultEnum;
import com.xiao.sell.exception.SellException;
import com.xiao.sell.form.OrderForm;
import com.xiao.sell.service.BuyerService;
import com.xiao.sell.service.OrderService;
import com.xiao.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid  OrderForm orderForm, BindingResult bindingResult){
       log.info("url={}","/buyer/order/create");
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO convert = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(convert.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO orderDTO = orderService.create(convert);

        Map<String,String> map=new HashMap<>();
        map.put("orderId",orderDTO.getOrderId());

        return ResultVOUtil.success(map);
    }

    @GetMapping("/list")
    public  ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        //防止恶意传入数据参数就可以访问
       // orderService.findOne(orderId)
        OrderDTO orderOne = buyerService.findOrderOne(openid, orderId);

        return ResultVOUtil.success(orderOne);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        //防止恶意传入数据参数就可以访问
     //    orderService.cancel(orderId)
        OrderDTO cancelOrder = buyerService.cancelOrder(openid, orderId);

        return ResultVOUtil.success();
    }
}
