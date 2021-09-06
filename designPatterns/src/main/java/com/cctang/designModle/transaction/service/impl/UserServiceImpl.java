package com.cctang.designModle.transaction.service.impl;

import com.cctang.designModle.transaction.dao.UserDao;
import com.cctang.designModle.transaction.service.UserService;
import com.cctang.springcloud.entities.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/12 22:40
 * @description
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }
}
