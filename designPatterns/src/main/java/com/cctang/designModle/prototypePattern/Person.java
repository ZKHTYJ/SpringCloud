package com.cctang.designModle.prototypePattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 22:18
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Cloneable{
    public String name; //名称


    public Person Clone(){
        Person person =null;

        try{
            person =new Person();

            person.name=this.name;
        }catch (Exception e){
            new RuntimeException(e);
        }

        return person;
    }
}
