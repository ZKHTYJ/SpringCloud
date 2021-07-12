package com.cctang.springcloud.dao;

import com.cctang.springcloud.entities.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 14:06
 */
@Mapper
public interface OrderDao {

    @Insert("insert into seckillorder(productId, amount) values(#{productId},#{amount})")
    int saveOrder(Order order);

    @Update("update seckillorder set productId=#{productId}, amount=#{amount} where id = #{id} ")
    int updateOrder(Order order);

    @Select("select * from seckillorder where id = #{id}")
    Order getOrderId(@Param("id") Long id);

    @Select("select * from seckillorder")
    List<Order> getAllOrders();
    @Delete("delete from seckillorder where id = #{id}")
    int deleteOrder(@Param("id") Long id);
    void seckill(@Param("productId") Long productId);

}
