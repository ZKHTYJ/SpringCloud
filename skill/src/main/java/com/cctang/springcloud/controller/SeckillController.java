package com.cctang.springcloud.controller;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Constants;
import com.cctang.springcloud.entities.Order;
import com.cctang.springcloud.entities.Product;
import com.cctang.springcloud.service.OrderService;
import com.cctang.springcloud.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 14:27
 */
@RestController
@Slf4j
@RequestMapping(value = "/seckill")
public class SeckillController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private ProductService productService;
    @Resource
    private OrderService orderService;
    @Resource
    private ZooKeeper zooKeeper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static ConcurrentHashMap<Long, Boolean> productSoldMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, Boolean> getProductSoldMap() {
        return productSoldMap;
    }

    /**
     * 将商品加载到redis
     * */
    @PostConstruct
    public void init(){
       List<Product> allProducts = productService.getAllProducts();
       for(Product product : allProducts){
           //jedis.set(k,v)
           stringRedisTemplate.opsForValue().set(Constants.REDIS_PRODUCT_STOCK_PREFIX + product.getId(), product.getStock()+"");
       }
    }
    /**
     * 商品秒杀
     * */
    @PostMapping("/m/{productId}")
    public CommonResult secKill(@PathVariable("productId") Long productId) throws KeeperException, InterruptedException {

        if(productSoldMap.get(productId) != null){
            log.info("=============哈哈===========");
            return new CommonResult(500,"商品已售完"+serverPort,null);
        }
        // 减库存 jedis.decr()
        Long stock = stringRedisTemplate.opsForValue().decrement(Constants.REDIS_PRODUCT_STOCK_PREFIX + productId);
            if(stock < 0){
                productSoldMap.put(productId, true);
                log.info("==============设置商品{}售完标记=============",+productId);
                // 防止少卖
                stock= stringRedisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + productId);
                log.info("================stock:" + stock);

                String zkSoldOutProductPath = Constants.getZKSoldOutProductPath(productId);
                if(zooKeeper.exists(zkSoldOutProductPath,true) == null){
                    zooKeeper.create(zkSoldOutProductPath,"true".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }else if("false".equals(new String(zooKeeper.getData(zkSoldOutProductPath,true,new Stat())))){
                    zooKeeper.setData(zkSoldOutProductPath,"true".getBytes(),-1);
                }
                    // 监听zk售完标记节点
                zooKeeper.exists(zkSoldOutProductPath,true);
                log.info("===============hehe===============");
                return new CommonResult(500,"商品已售完"+serverPort,null);
            }
        try{
            orderService.seckill(productId);
        }catch (Exception e) {
            // 下单失败 把扣减的库存商品在加回去 jedis.incr()
            stringRedisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + productId);
            if(productSoldMap.get(productId) != null){
                productSoldMap.remove(productId);
            }
            // 修改zk的商品售完标记为false
            if(zooKeeper.exists(Constants.getZKSoldOutProductPath(productId),true) !=null){
                zooKeeper.setData(Constants.getZKSoldOutProductPath(productId),"false".getBytes(),-1);
            }
            log.error("创建订单失败", e);
            return new CommonResult(500,"秒杀失败,当前端口号为："+serverPort, null);
        }

        return new CommonResult(200,"秒杀成功,当前端口号为："+serverPort);
    }

    /**
     * 查询单个
     * */
    @GetMapping(value ="/{id}")
    public CommonResult getProductforId(@PathVariable("id") Long id) {
        // 查看此id是否存在

        Product product = productService.getProductId(id);
        log.info("此次查詢的結果："+product+"当前端口号为："+serverPort);
        if(product != null) {
            return new CommonResult(200,"查詢成功,当前端口号为："+serverPort, product);
        }else {
            return new CommonResult(500,"抱歉，沒有id為"+id+"的記錄,当前端口号为："+serverPort, null);
        }
    }
    @PostMapping(value = "/order/addOrder")
    public CommonResult addOrder(@RequestBody Order order){
         int result = orderService.saveOrder(order);
        log.info("此次插入的結果："+result+"当前端口号为："+serverPort);
        if(result > 0) {
            return new CommonResult(200,"插入成功,当前端口号为："+serverPort, result);
        }else {
            return new CommonResult(500,"插入失敗,当前端口号为："+serverPort, null);
        }
    };

}
