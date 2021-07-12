package com.cctang.springcloud.service;

import com.cctang.springcloud.entities.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 13:53
 */
public interface ProductService {

    int saveProduct(Product product);
    int updateProduct(Product product);
    Product getProductId(@Param("id") Long id);
    List<Product> getAllProducts();
    int deductProductStock(@Param("id") Long id);
    int deleteProduct(@Param("id") Long id);
}
