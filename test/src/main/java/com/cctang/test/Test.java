package com.cctang.test;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/27 22:09
 */
public class Test {


    public static void main(String[] args) throws ClassNotFoundException {
//        Integer a = 127;
//        Integer b = 127;
//        System.out.println(a == b);
//      Test t = new Test();
//      t.m();
//        String a = new String("ab"); // a 为一个引用
//        String b = new String("ab"); // b为另一个引用,对象的内容一样
//        String aa = "ab"; // 放在常量池中
//        String bb = "ab"; // 从常量池中查找
//        if (aa == bb) // true
//            System.out.println("aa==bb");
//        if (a == b) // false，非同一对象
//            System.out.println("a==b");
//        if (a.equals(b)) // true
//            System.out.println("aEQb");
//        if (42 == 42.0) { // true
//            System.out.println("true");
//        }
//        Object a = new Object(); // a 为一个引用
//        Object b = new Object(); // b为另一个引用,对象的内容一样
//        Object aa = "ab"; // 放在常量池中
//        Object bb = "ab"; // 从常量池中查找
//        if (aa == bb) // true
//            System.out.println("aa==bb");
//        if (a == b) // false，非同一对象
//            System.out.println("a==b");
//        if (a.equals(b)) // false
//            System.out.println("aEQb");
//        if (42 == 42.0) { // true
//            System.out.println("true");
//        }

        //反射
        //方式一(通过建立对象)
            A a = new A();
        Class classobj1 = a.getClass();
        System.out.println(classobj1.getName());
        //方式二（所在通过路径-相对路径）
        Class classobj2 = Class.forName("com.cctang.test.A");
        System.out.println(classobj2.getName());
        //方式三（通过类名）
        Class classobj3 = A.class;
        System.out.println(classobj3.getName());
    }


public void m () {
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            System.out.println("i=" + i + ",j=" + j);
            if (j == 5) {
                continue ;
            }

        }
        System.out.println("11111111111");
    }

}
}

