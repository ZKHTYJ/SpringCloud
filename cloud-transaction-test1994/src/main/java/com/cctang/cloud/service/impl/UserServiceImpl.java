package com.cctang.cloud.service.impl;

import com.cctang.cloud.dao.UserDao;
import com.cctang.cloud.service.UserService;
import com.cctang.springcloud.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/18 21:27
 *
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