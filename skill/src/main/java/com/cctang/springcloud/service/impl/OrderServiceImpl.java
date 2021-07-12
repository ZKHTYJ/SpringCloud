package com.cctang.springcloud.service.impl;

import com.cctang.springcloud.dao.OrderDao;
import com.cctang.springcloud.entities.Order;
import com.cctang.springcloud.entities.Product;
import com.cctang.springcloud.service.OrderService;
import com.cctang.springcloud.service.ProductService;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 14:10
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private ProductService productService;
    @Override
    public int saveOrder(Order order) {
        return orderDao.saveOrder(order);
    }

    @Override
    @Transactional
    public int updateOrder(Order order) {
        return orderDao.updateOrder(order);
    }

    @Override
    public Order getOrderId(Long id) {
        return orderDao.getOrderId(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    @Transactional
    public int deleteOrder(Long id) {
        return orderDao.deleteOrder(id);
    }
    @Override
    @Transactional
    public void seckill(Long productId){
        //查询商品
        Product product = productService.getProductId(productId);

        if(product.getStock() <= 0){
            throw new RuntimeException("商品已售完");
        }

        //创建秒杀订单
        Order order = new Order();
        //order.setId(productId);
        order.setProductId(productId);
        order.setAmount(product.getPrice());
        saveOrder(order);

        // 减库存
        int updateNum = productService.deductProductStock(productId);
        if(updateNum <= 0){
            throw new RuntimeException("商品已售完");
        }

    }
}
