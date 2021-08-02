package com.cctang.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 14:44
 * @description 产品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    private String food;
    private String drink;

}
