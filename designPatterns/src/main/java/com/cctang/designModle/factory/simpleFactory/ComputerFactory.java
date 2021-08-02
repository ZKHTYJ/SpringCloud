package com.cctang.designModle.factory.simpleFactory;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 23:33
 * @description
 */
public class ComputerFactory {
    public static IComputer createComputer(String type){
        IComputer computer=null;
        switch (type){
            case "lenovo":
                computer=new LenovoComputer();
                break;
            case "hp":
                computer=new HpComputer();
                break;
            case "asus":
                computer=new AsusComputer();
                break;
        }
        return computer;
    }
}
