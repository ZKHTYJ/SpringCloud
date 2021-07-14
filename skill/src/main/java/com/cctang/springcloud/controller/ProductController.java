package com.cctang.springcloud.controller;

import com.alibaba.fastjson.JSON;
import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Constants;
import com.cctang.springcloud.entities.Product;
import com.cctang.springcloud.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/14 22:35
 */
@RestController
@Slf4j
@RequestMapping(value = "/query")
public class ProductController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private ProductService productService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 查询单个
     * */
    @GetMapping(value ="/{id}")
    public CommonResult getProductforId(@PathVariable("id") Long id) {
        Product product = productService.getProductId(id);
        log.info("此次查詢的結果："+product+"当前端口号为："+serverPort);
        if(product != null) {
            return new CommonResult(200,"查詢成功,当前端口号为："+serverPort, product);
        }else {
            return new CommonResult(500,"抱歉，沒有id為"+id+"的記錄,当前端口号为："+serverPort, null);
        }
    }

    /**
     * 查询全部
     * */
    @GetMapping(value = "/product/all")
    public CommonResult<List> getAllProducts() {
        List<Product> allProducts = null;
        String s1 = stringRedisTemplate.opsForValue().get("productList");
        if(s1 != "") {
            //数据库操作
            allProducts = productService.getAllProducts();
            String s = JSON.toJSONString(allProducts);
            stringRedisTemplate.opsForValue().set("productList",s);
        } else {
            allProducts = JSON.parseArray(s1,Product.class);
        }

        // List<Product> allProducts = productService.getAllProducts();
        if(allProducts.size() != 0){
            log.info("此次查询到秒杀商品的列表为： "+allProducts);
            return new CommonResult<>(200,"查询所有秒杀产品成功",allProducts);
        }else{
            return new CommonResult<>(204,"当前暂无秒杀商品",null);
        }
    }
}

