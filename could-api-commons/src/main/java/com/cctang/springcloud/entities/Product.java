package com.cctang.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 13:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String pic;
}
