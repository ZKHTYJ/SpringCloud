package com.cctang.designModle.singleton;

import com.cctang.springcloud.entities.PrintSpoolerException;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 15:04
 * @description 在操作系统中，打印池(Print Spooler)是一个用于管理打印任务的应用程序，通过打印池用户可以删除、中止或者改变打印任务的优先级，
 * 在一个系统中只允许运行一个打印池对象，如果重复创建打印池则抛出异常。
 * 现使用单例模式来模拟实现打印池的设计
 */
public class PrintSpoolerSingleton {
    private static PrintSpoolerSingleton instance=null;

    private PrintSpoolerSingleton() {
    }

    public static PrintSpoolerSingleton getInstance() throws PrintSpoolerException {
        if(instance ==null){
            System.out.println("创建打印池！");
            instance=new PrintSpoolerSingleton();
        }else{
            throw new PrintSpoolerException("打印池正在工作中！");
        }
        return instance;
    }
    public void manageJobs()
    {
        System.out.println("管理打印任务！");
    }
}
