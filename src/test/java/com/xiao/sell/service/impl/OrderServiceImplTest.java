package com.xiao.sell.service.impl;

import com.xiao.sell.domain.OrderDetail;
import com.xiao.sell.dto.OrderDTO;
import com.xiao.sell.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO=new OrderDTO();
        List<OrderDetail> orderDetails=new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123444");
        orderDetail.setProductQuantity(2);
        orderDetails.add(orderDetail);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("123456");
        orderDetail2.setProductQuantity(3);
        orderDetails.add(orderDetail2);

        orderDTO.setBuyerAddress("成都");
        orderDTO.setBuyerName("大大");
        orderDTO.setBuyerOpenid("11121112");
        orderDTO.setBuyerPhone("1112");
        orderDTO.setOrderDetailList(orderDetails);
        orderService.create(orderDTO);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1526823744789345568");
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() throws Exception {

        Page<OrderDTO> orderDTOS = orderService.findList("11121113", new PageRequest(0, 2));
        Assert.assertNotEquals(0,orderDTOS.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1526823744789345568");
        OrderDTO cancel = orderService.cancel(orderDTO);
        Assert.assertNotNull(cancel);
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1526823744789345568");
        OrderDTO finish = orderService.finish(orderDTO);
        Assert.assertNotNull(finish);
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1526823744789345568");
        OrderDTO paid = orderService.paid(orderDTO);
        Assert.assertNotNull(paid);
    }

    @Test
    public void findList1() throws Exception {
    }

}