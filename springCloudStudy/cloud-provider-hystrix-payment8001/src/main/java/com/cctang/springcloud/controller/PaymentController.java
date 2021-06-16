package com.cctang.springcloud.controller;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Payment;
import com.cctang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/30 21:53
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    Boolean flag ;
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private DiscoveryClient discoveryClint;

    /**
     * 添加
     * */
    @PostMapping(value = "/payment/create")
    public CommonResult creat(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("此次插入的結果："+result+"当前端口号为："+serverPort);
        if(result > 0) {
            return new CommonResult(200,"插入成功,当前端口号为："+serverPort, result);
        }else {
            return new CommonResult(500,"插入失敗,当前端口号为："+serverPort, null);
        }
    }
    /**
     * 查询单个
     * */
    @GetMapping(value ="/payment/get/{id}")
    public CommonResult getPaymentforId(@PathVariable("id") Long id) {
        // 查看此id是否存在
        if (!checkPaymentId(id)) return new CommonResult(500, "抱歉，沒有id為" + id + "的記錄", null);
        Payment payment = paymentService.getPaymentId(id);
        log.info("此次查詢的結果："+payment+"当前端口号为："+serverPort);
        if(payment != null) {
            return new CommonResult(200,"查詢成功,当前端口号为："+serverPort, payment);
        }else {
            return new CommonResult(500,"抱歉，沒有id為"+id+"的記錄,当前端口号为："+serverPort, null);
        }
    }

    private  Boolean checkPaymentId(@PathVariable("id") Long id) {
        List<Payment> paymentList = paymentService.getAllPayments();
        List<Long> idList = new ArrayList<Long>();
        for(Payment payment : paymentList){
            // 取出id放入idList集合
            idList.add(payment.getId());
        }
        if(idList.contains(id)){
            flag = true;
        }else {
            flag=false;
        }
        return flag;
    }

    /**
     * 查询所有payment
     * */
    @GetMapping(value = "/payment/get/all")
    public CommonResult<List> getAllPayments() {
        List<Payment> paymentList = paymentService.getAllPayments();
        log.info("此次查詢的結果集合为："+paymentList+"当前端口号为："+serverPort);
        if(paymentList.size() != 0) {
            return new CommonResult<List>(201,"查询成功,当前端口号为："+serverPort,paymentList);
        }else {
            return new CommonResult<List>(500,"未查询到任何数据,当前端口号为："+serverPort,null);
        }
    }

    /**
     * 删除payments
     * */
    //@RequestMapping(value = "/payment/del")
    @PostMapping(value = "/payment/del")
    public CommonResult delPaymentId(Long id){
        // 查看此id是否存在
        if (!checkPaymentId(id)) return new CommonResult(500, "抱歉，沒有id為" + id + "的記錄", null);
        int result = paymentService.delPayment(id);
        log.info("此次删除的paymentId为："+id+"当前端口号为："+serverPort);
        if(result > 0) {
            return new CommonResult(200,"删除成功,当前端口号为："+serverPort, "删除数据的id为："+id);
        }else{
            return new CommonResult(500,"删除失败,当前端口号为："+serverPort,null);
        }
    }
    /**
     * 修改payments
     * */
    @RequestMapping(value = "payment/modify")
    public CommonResult modifyPayment(@RequestBody Payment payment) {
        // 查看此id是否存在
        if (!checkPaymentId(payment.getId())) return new CommonResult(500, "抱歉，沒有id為" + payment.getId() + "的記錄", null);
        int result = paymentService.updatePayment(payment);
        log.info("此次修改的paymentId为："+payment.getId()+"当前端口号为："+serverPort);
        if(result>0){
            return new CommonResult(204,"修改成功,当前端口号为："+serverPort, "修改的payment的id为："+payment.getId());
        }else {
            return new CommonResult(500,"修改失败,当前端口号为："+serverPort, null);
        }
    }
    @GetMapping(value="/payment/discovery")
    public Object discoverClient(){
        List<String> discoveryClintSercices = discoveryClint.getServices();
        for(String element : discoveryClintSercices){
            log.info("**********element" + element);
        }

        List<ServiceInstance> discoveryClintInstances = discoveryClint.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance : discoveryClintInstances){
            log.info(instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClint;
    }
    @GetMapping(value = "/payment/lb")
    public String lb () {
        return serverPort;
    }

    @GetMapping(value = "/payment/timeout")
    public String feignTimeOut()  {
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    };


    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_OK(id);
        log.info("*******result:"+result);
        return result;
    }
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("*******result:"+result);
        return result;
    }
    @GetMapping(value = "/payment/circuit/{id}")
    public String checkId(@PathVariable("id") Integer id){
        return paymentService.paymentCircuitBreaker(id);
    }
}
