package com.cctang.export;

import lombok.Data;
import org.hamcrest.Condition;

import java.util.Date;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/10/19 20:43
 * @description
 */
@Data
public class EmsMeter {
    private Integer meterId;
    private String meterCode;
    private Integer companyId;
    private String meterName;
    private Date createDate;
}
