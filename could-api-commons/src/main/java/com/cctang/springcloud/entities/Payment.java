package com.cctang.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/16 22:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
private Long id;
private String serial;
}
