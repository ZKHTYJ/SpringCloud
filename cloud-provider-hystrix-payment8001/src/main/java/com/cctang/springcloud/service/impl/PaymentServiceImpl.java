package com.cctang.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cctang.springcloud.dao.PaymentDao;
import com.cctang.springcloud.entities.Payment;
import com.cctang.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/30 21:53
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
// ============服务降级
    //成功
    @Override
    public String paymentInfo_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_OK,id：  "+id+"\t"+"哈哈哈"  ;
    }

    //失败
     @Override
     @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandle", commandProperties = {
             @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
     })
    public String paymentInfo_TimeOut(Integer id){
        try { TimeUnit.MILLISECONDS.sleep(3000); }catch (Exception e) {e.printStackTrace();}
        return "线程池："+Thread.currentThread().getName()+"paymentInfo_TimeOut,id：  "+id+"\t"+"哎哎哎";

}

    public String paymentInfo_TimeOutHandle(Integer id){
        return "线程池："+Thread.currentThread().getName()+"系统繁忙或运行报错，请稍后再试,id：  "+id+"\t"+"呜呜呜";
    }
// ====================服务熔断
@HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
        @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),   //请求次数
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),  //时间范围
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), //失败率达到多少后跳闸
})

public String paymentCircuitBreaker(@PathVariable("id") Integer id){
    if (id < 0){
        throw new RuntimeException("*****id 不能负数");
    }
    String serialNumber = IdUtil.simpleUUID();

    return Thread.currentThread().getName()+"\t"+"调用成功,流水号："+serialNumber;
}
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " +id;
    }
}
