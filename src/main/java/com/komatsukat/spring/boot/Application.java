package com.komatsukat.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription
 * @date 17:18 2018/7/27
 */
@SpringBootApplication
// @ComponentScan(basePackages = {"com.komatsukat.spring.boot"}) 程序只加载Application.java所在包及其子包下的内容。
public class Application {

    //在main方法中启动程序
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
