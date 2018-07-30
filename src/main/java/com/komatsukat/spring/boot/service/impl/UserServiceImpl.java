package com.komatsukat.spring.boot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.komatsukat.spring.boot.dao.UserDao;
import com.komatsukat.spring.boot.domain.UserDto;
import com.komatsukat.spring.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription 用户业务service实现类
 * @date 15:51 2018/7/30
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int insertUser(UserDto user) {
        return userDao.insertUser(user);
    }

    @Override
    public PageInfo<UserDto> selectUserPaged(int pageNum, int pageSize) {

        // 实现物理分页
        PageHelper.offsetPage(pageNum, pageSize);

        List<UserDto> userDtos = userDao.selectAllUsers();

        PageInfo<UserDto> pageInfo = new PageInfo<>();
        pageInfo.setList(userDtos);
        return pageInfo;
    }
}
