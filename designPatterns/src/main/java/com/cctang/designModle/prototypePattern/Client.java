package com.cctang.designModle.prototypePattern;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 22:30
 * @description 现在为了快速复制同一工厂的不同手机，解决这一实际应用场景。
 */
public class Client {
    public static void main(String[] args) {
        Phone phone =new Phone();
        phone.setName("华为Mate40pro");
        phone.setPrice(6499);

        phone.factory = new Factory();
        phone.factory.setName("联发科");

        Person person =new Person();
        person.setName("任正非");
        phone.factory.Manager=person;

        Phone phone1 =phone.Clone();
        phone1.setName("华为Mate40pro保时捷");
        phone1.factory.setName("台积电");
        System.out.println(phone.toString());
        System.out.println(phone1.toString());
    }
}
