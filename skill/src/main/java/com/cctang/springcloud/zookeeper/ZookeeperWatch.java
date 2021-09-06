package com.cctang.springcloud.zookeeper;

import com.cctang.springcloud.controller.SeckillController;
import com.cctang.springcloud.entities.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.apache.zookeeper.Watcher.Event.EventType;
/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 20:20
 */
@Component
@Slf4j
public class ZookeeperWatch implements Watcher, ApplicationContextAware {

   private static  ApplicationContext applicationContext;

   private ZooKeeper zooKeeper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getType() == EventType.None && event.getPath() == null){
            log.info("====================zookeeper连接成功====================");
            if(zooKeeper == null){
                zooKeeper = applicationContext.getBean(ZooKeeper.class);
            }
            // 创建zk的商品售完标记根节点
            try{

                if(zooKeeper.exists(Constants.ZK_PRODUCT_SOLD_OUT_FLAG,false) == null){
                    zooKeeper.create(Constants.ZK_PRODUCT_SOLD_OUT_FLAG, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            }catch(Exception e){
                e.printStackTrace();

            }
        } else if(event.getType() == Event.EventType.NodeDataChanged) {
            try {
                String path = event.getPath();
                String soldOutFlag = new String(zooKeeper.getData(path,true,new Stat()));
                log.info("zookeeper数据节点修改变动，path={}, value={}",path,soldOutFlag);
                if("false".equals(soldOutFlag)){
                    String productId = path.substring(path.lastIndexOf("/")+1,path.length());
                    SeckillController.getProductSoldMap().remove(productId);
                }
            } catch (Exception e) {
              log.error("zookeeper数据节点修改回调时间异常", e);
            }
        }
    }
}
