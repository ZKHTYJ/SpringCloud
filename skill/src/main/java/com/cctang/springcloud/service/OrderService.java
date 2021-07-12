package com.cctang.springcloud.service;

import com.cctang.springcloud.entities.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 14:10
 */
public interface OrderService {
    int saveOrder(Order order);
    int updateOrder(Order order);
    Order getOrderId(@Param("id") Long id);
    List<Order> getAllOrders();
    int deleteOrder(@Param("id") Long id);
    void seckill(@Param("productId") Long productId);
}
