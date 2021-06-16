package com.cctang.springcloud.service;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/31 22:02
 */
@Component
public class PaymentFeignServiceImpl implements PaymentFeignService{
    @Override
    public CommonResult<Payment> getPaymentforId(Long id) {
        return null;
    }

    @Override
    public String paymentServiceTimeOut() {
        return null;
    }

    @Override
    public String paymentInfo_OK(Integer id) {
        return "抱歉，paymentInfo_OK 对方服务已宕机或者关停~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "抱歉，paymentInfo_TimeOut 对方服务已宕机或者关停~~";
    }
}
