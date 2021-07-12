package com.cctang.springcloud.service.impl;

import com.cctang.springcloud.dao.ProductDao;
import com.cctang.springcloud.entities.Product;
import com.cctang.springcloud.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 13:53
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductDao productDao;
    @Override
    public int saveProduct(Product product) {
        return productDao.saveProduct(product);
    }

    @Override
    public int updateProduct(Product product) {
        return productDao.updateProduct(product) ;
    }

    @Override
    public Product getProductId(Long id) {
        return productDao.getProductId(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public int deductProductStock(Long id) {
        return productDao.deductProductStock(id);
    }

    @Override
    public int deleteProduct(Long id) {
        return productDao.deleteProduct(id);
    }
}
