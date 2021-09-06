package com.cctang.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/12 22:15
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int id;
    private String type;
}
