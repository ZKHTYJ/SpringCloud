package com.cctang.springcloud.entities;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 15:09
 * @description
 */
public class PrintSpoolerException extends Exception{
    public PrintSpoolerException () {
        super();
    }
    public PrintSpoolerException (String message) {
        super(message);
    }
}
