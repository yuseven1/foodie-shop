package com.yuseven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.yuseven.mapper")
@ComponentScan(basePackages = {"com.yuseven","org.n3r.idworker"})
@EnableScheduling   // 开启定时任务
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
