package com.cctang.springcloud.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cctang.springcloud.pojo.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/31 2:07
 * @description
 */
@SpringBootTest
class UserMapperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    // 测试查询全部用户
    public void queryAllUser(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    // 测试插入用户
    @Test
    public void insertUsers(){
        User user = new User();
        user.setAge(11);
        user.setEmail("test8@qq.com");
        user.setName("cc");
        int insert = userMapper.insert(user);
        if(insert>0){
            System.out.println("插入成功");
        }else{
            System.out.println("插入失败");
        }
    }

    // 更新用户
    @Test
    public void updateUser(){
        User user = new User();
        user.setAge(18);
        user.setEmail("test8@qq.com");
        user.setName("cctang");
        user.setId(1433425416780017665L);
        int i = userMapper.updateById(user);
        if(i>0){
            System.out.println("更新成功");
        }else{
            System.out.println("更新失败");
        }
    }

    // 测试成功乐观锁
    @Test
    public void optimisticLockerSuccess(){
        User user = userMapper.selectById(1L);
        user.setName("mj");
        user.setEmail("test9@qq.com");
        user.setId(1L);
        int i = userMapper.updateById(user);
        if(i>0){
            System.out.println("更新成功");
        }else{
            System.out.println("更新失败");
        }
    }
    // 测试失败乐观锁
    @Test
    public void optimisticLockerFail(){
        //线程1
        User user = userMapper.selectById(1L);
        user.setName("mjgg");
        user.setEmail("test19@qq.com");

        //线程2
        User user2 = userMapper.selectById(1L);
        user2.setName("mjcc");
        user2.setEmail("test10@qq.com");
        userMapper.updateById(user2);

        // 自旋锁来多次尝试提交
        userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值
    }


    // 查询批量用户
    @Test
    public void searchUsers(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
            users.forEach(System.out::println);
    }

    @Test
    public void searchUserById(){
        User user = userMapper.selectById(1434892196437721090L);
        System.out.println(user);
    }

    // 按条件查询之一---map
    @Test
    public void queryCondition(){
        HashMap<String, Object> map = new HashMap<>();
        // 自定义查询
        map.put("name","mjcc");
        map.put("age",18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);

    }

    @Test
    // 分页查询
    public void queryPage(){
        Page<User> page = new Page<>(1,3);
        IPage<User> userIPage = userMapper.selectPage(page, null);

        userIPage.getRecords().forEach(System.out::println);
        System.out.println("condition: " + userIPage.condition());
        System.out.println("getCurrent: " +userIPage.getCurrent());
        System.out.println("getPages: " +userIPage.getPages());
        System.out.println("getSize: " +userIPage.getSize());
        System.out.println("getTotal: " +userIPage.getTotal());
        System.out.println("offset: " +userIPage.offset());
    }


    /**
        * 物理删除
     * */
    //删除---单一
    @Test
    public void delRecode(){
        userMapper.deleteById(1433048262804328449L);
    }

    // 删除---批量
    @Test
    public void delIds(){
        userMapper.deleteBatchIds(Arrays.asList(2L,3L));
    }

    // 删除---条件删除

    @Test
    public void delIdMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","cctang");
        userMapper.deleteByMap(map);
    }

    /**
     * 逻辑删除
     * */

}