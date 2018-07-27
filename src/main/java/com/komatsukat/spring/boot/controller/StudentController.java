package com.komatsukat.spring.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription spring-boot learn
 * @date 16:39 2018/7/27
 */

@RestController
public class StudentController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

}
