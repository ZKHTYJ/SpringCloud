package com.cctang.springcloud.dao;

import com.cctang.springcloud.entities.Payment;
import com.cctang.springcloud.entities.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 13:44
 */
@Mapper
public interface ProductDao {
    @Insert("insert into product(id, name, price,stock,pic) values(#{id},#{name},#{price},#{stock},#{pic})")
    int saveProduct(Product product);

    @Update("update product set name=#{name}, price=#{price}, stock=#{stock}, pic=#{pic} where id = #{id} ")
    int updateProduct(Product product);

    @Select(" select * from product where id=#{id}")
    Product getProductId(@Param("id") Long id);

    @Select("select * from product")
    List<Product> getAllProducts();

    @Update("update product set stock=stock-1 where id=#{id} and stock>0")
    int deductProductStock(@Param("id") Long id);

    @Delete("delete from product where id = #{id}")
    int deleteProduct(@Param("id") Long id);
}
