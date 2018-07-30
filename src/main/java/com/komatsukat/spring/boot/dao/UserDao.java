package com.komatsukat.spring.boot.dao;

import com.komatsukat.spring.boot.domain.UserDto;

import java.util.List;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription
 * @date 15:20 2018/7/30
 */
public interface UserDao {

    /**
     * 新增用户
     * @param user
     * @return
     */
    int insertUser(UserDto user);

    /**
     * 查询所有用户
     * @return
     */
    List<UserDto> selectAllUsers();
}
