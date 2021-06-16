package com.cctang.springcloud.service;

import com.cctang.springcloud.dao.PaymentDao;
import com.cctang.springcloud.service.PaymentService;
import com.cctang.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/22 20:04
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;
    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentId(Long id) {
        return paymentDao.getPaymentId(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentDao.getAllPayments();
    }

    @Override
    public int delPayment(Long id){
        return paymentDao.delPayment(id);
    }

    @Override
    public int updatePayment(Payment payment) {
        return paymentDao.updatePayment(payment);
    }
}

