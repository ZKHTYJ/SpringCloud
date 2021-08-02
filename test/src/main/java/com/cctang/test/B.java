package com.cctang.test;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/27 22:12
 */
public class B extends A {

    public void a1(){
        System.out.println("我是一个final修饰的方法");
    }
}
class Person{
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }
    public Person(String name, int age) {
        this(name);
        this.age = age;
    }
}
