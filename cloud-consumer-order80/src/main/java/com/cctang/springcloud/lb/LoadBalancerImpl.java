package com.cctang.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/27 23:13
 */
@Component
public class LoadBalancerImpl implements LoadBalancer {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        // 实现自旋锁
        do {
            current = this.atomicInteger.get();
            next = current > 2147483647 ? 0 : current+1;
        }while (!this.atomicInteger.compareAndSet(current,next)); //CAS
        System.out.println("********next" + next);
        return next;
    };
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
