package com.cctang.designModle.prototypePattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 22:16
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone implements Cloneable{
    public String name; //手机名称

    public float price; //价钱

    public Factory factory; //工厂


    public Phone Clone(){
        Phone phone = null;
        try{
            phone=new Phone();

            if(this.name!=null){
                phone.name=this.name;
            }

            if(this.price!=0){
                phone.price=this.price;
            }

            if(this.factory!=null){
                phone.factory=this.factory.Clone();
            }
        }catch(Exception e){
            new  RuntimeException(e);
        }
        return phone;
    }
}
