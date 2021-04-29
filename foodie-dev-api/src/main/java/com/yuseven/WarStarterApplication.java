package com.yuseven;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/4/2 1:44
 */
// 打包war [4] 增加war的启动类
public class WarStarterApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 指向Application这个springboot的启动类
        return builder.sources(Application.class);
    }
}
