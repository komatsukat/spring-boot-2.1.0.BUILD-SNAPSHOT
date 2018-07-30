package com.komatsukat.spring.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription 主配置类
 * 1. Spring Boot建议将我们main方法所在的这个主要的配置类配置在根包名下。
 *
 * 2. {@ComponentScan(basePackages = {"com.komatsukat.spring.boot"})} 程序只加载Application.java所在包及其子包下的内容，
 * 通过该注解可以配置要扫描的包及其子包，从而不需要放在根包名下。
 * 因为默认和包有关的注解，默认包名都是当前类所在的包，例如{@ComponentScan}, {@EntityScan}, {@SpringBootApplication}注解。
 *
 * 3. {@RestController} 因为我们例子是写一个web应用，因此写的这个注解，这个注解相当于同时添加{@Controller}和{@ResponseBody}注解。
 *
 * 4. {@EnableAutoConfiguration} Spring Boot建议只有一个带有该注解的类。Spring Boot会自动根据你jar包的依赖来自动配置项目。
 * 例如当你项目下面有HSQLDB的依赖时，Spring Boot会创建默认的内存数据库的数据源DataSource，如果你自己创建了DataSource，Spring Boot就不会创建默认的DataSource。
 * 如果你不想让Spring Boot自动创建，你可以配置注解的exclude属性：{@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})}
 *
 * 5. {@SpringBootApplication} 由于大量项目都会在主要的配置类上添加{@Configuration},{@EnableAutoConfiguration},{@ComponentScan}三个注解。
 * 因此Spring Boot提供了{@SpringBootApplication}注解，该注解可以替代上面三个注解（使用Spring注解继承实现）。
 *
 *
 * @date 17:18 2018/7/27
 */
@SpringBootApplication
@MapperScan("com.komatsukat.spring.boot.dao")
public class Application {

    /**
     * 6. {@RequestMapping("/")}这个注解起到路由的作用
     */
    @RequestMapping("/")
    public String hello() {
        return "hello kitty";
    }

    //在main方法中启动程序
    public static void main(String[] args) {
        /**
         * 7. 启动Spring Boot项目最简单的方法就是执行下面的方法，该方法返回一个ApplicationContext对象，
         * 使用注解的时候返回的具体类型是AnnotationConfigApplicationContext或AnnotationConfigEmbeddedWebApplicationContext，
         * 当支持web的时候是第二个。
         */
        SpringApplication.run(Application.class, args);

        /**
         * 8. 除了上面这种方法外，还可以用下面的方法，
         * SpringApplication包含了一些其他可以配置的方法，如果你想做一些配置，可以用这种方式。
         */
        /*SpringApplication application = new SpringApplication(Application.class);
        application.run(args);*/

        /**
         * 9. 除了上面这种直接的方法外，还可以使用SpringApplicationBuilder。
         * 当使用SpringMVC的时候由于需要使用子容器，就需要用到SpringApplicationBuilder，该类有一个child(xxx...)方法可以添加子容器。
         */
        /*new SpringApplicationBuilder()
                .sources(Application.class)
                .run(args);*/
    }

}
