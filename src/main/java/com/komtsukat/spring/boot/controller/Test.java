package com.komtsukat.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription spring-boot learn
 * @date 16:39 2018/7/27
 */

@RestController
public class Test {

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

}
