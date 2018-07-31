package com.komatsukat.spring.boot.web.controller;

import com.komatsukat.spring.boot.domain.UserDto;
import com.komatsukat.spring.boot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription spring-boot learn
 * @date 16:39 2018/7/27
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @ResponseBody
    @PostMapping("/insertUser")
    public int insertUser(UserDto user) {
        log.info("request /user/insertUser begin");
        int userId = userService.insertUser(user);
        log.info("request /user/insertUser end");
        return userId;
    }

    @ResponseBody
    @GetMapping("/selectUserPaged")
    public Object selectUserPaged(@RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        log.info("request /user/selectUserPaged begin");
        List<UserDto> list = userService.selectUserPaged(pageNum, pageSize).getList();
        log.info("request /user/selectUserPaged end");
        return list;
    }
}
