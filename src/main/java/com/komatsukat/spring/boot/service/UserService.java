package com.komatsukat.spring.boot.service;

import com.github.pagehelper.PageInfo;
import com.komatsukat.spring.boot.domain.UserDto;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription
 * @date 15:51 2018/7/30
 */
public interface UserService {

    /**
     * 单个 新增用户
     * @param user
     * @return
     */
    int insertUser(UserDto user);

    /**
     * 分页接口
     * @return
     */
    PageInfo<UserDto> selectUserPaged(int pageNum, int pageSize);
}
