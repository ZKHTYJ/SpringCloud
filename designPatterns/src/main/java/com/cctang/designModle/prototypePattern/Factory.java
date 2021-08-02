package com.cctang.designModle.prototypePattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 22:17
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factory implements Cloneable{
    public String name;  //工厂名称
    public Person Manager; //负责人


    public Factory Clone(){
        Factory factory =null;
        try{
            factory=new Factory();

            if(this.name!=null){
                factory.name=this.name;
            }

            if(this.Manager!=null){
                factory.Manager=this.Manager.Clone();
            }

        }catch (Exception e){
            new RuntimeException(e);
        }

        return factory;
    }

}
