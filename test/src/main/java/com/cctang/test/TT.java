package com.cctang.test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/16 13:36
 * @description 在这中间可以添加N行代码，但必须保证s引用的指向不变，最终将输出变成abcd
 */
public class TT {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String s = new String("abc");
        System.out.println("第一次hashcode："+s.hashCode());
        System.out.println("第一次内存地址："+System.identityHashCode(s));
        Field value = s.getClass().getDeclaredField("value");
        value.setAccessible(true);
        value.set(s,"abcd".toCharArray());
        System.out.println(s);
        System.out.println("第二次hashcode："+s.hashCode());
        System.out.println("第一次内存地址："+System.identityHashCode(s));

        CopyOnWriteArrayList a = new CopyOnWriteArrayList();
// =========================错误示例========================
//        System.out.println("打印当前s值："+s);
//        System.out.println("第一次hashcode："+s.hashCode());
//        System.out.println("第一次内存地址："+System.identityHashCode(s));
//        String s1 = s + "d";
//        System.out.println("第二次hashcode："+s1.hashCode());
//        System.out.println("第一次内存地址："+System.identityHashCode(s1));

    }
}
