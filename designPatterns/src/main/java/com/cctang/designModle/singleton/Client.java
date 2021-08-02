package com.cctang.designModle.singleton;

import com.cctang.springcloud.entities.PrintSpoolerException;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 15:11
 * @description 模拟打印池工作
 */
public class Client {
    public static void main(String[] args) {
        PrintSpoolerSingleton ps1, ps2;
        try {
            ps1 = PrintSpoolerSingleton.getInstance();
            ps1.manageJobs();
            System.out.println(ps1.getClass().getName());
        } catch (PrintSpoolerException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("================分割线=================");
        try {
            ps2 = PrintSpoolerSingleton.getInstance();
            ps2.manageJobs();
            System.out.println(ps2.getClass().getName());

        } catch (PrintSpoolerException e) {
            System.out.println(e.getMessage());
        }

    }
}
