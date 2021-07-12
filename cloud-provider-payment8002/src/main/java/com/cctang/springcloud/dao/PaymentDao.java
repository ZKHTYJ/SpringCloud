package com.cctang.springcloud.dao;

import com.cctang.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/22 20:01
 */
@Mapper
public interface PaymentDao
{
    // 創建定單記錄
    public int create(Payment payment);
    // 查询单条数据
    public Payment getPaymentId(@Param("id") Long id);
    // 查询所有数据
    public List<Payment> getAllPayments();
    // 删除payment数据
    public int delPayment(@Param("id") Long id);
    // 修改payment
    public int updatePayment(Payment payment);
}
