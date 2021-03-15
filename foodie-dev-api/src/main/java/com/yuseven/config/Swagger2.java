package com.yuseven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 的配置类，需交给Spring容器管理，即加配置注解；
 * 另外需要开启Swagger2配置
 *
 *
 * @Author Yu Qifeng
 * @Date 2021/2/23 23:24
 * @Version v1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * 默认访问地址：http://localhost:8088/swagger-ui.html
     *
     * 引入github上的ui后的访问地址：http://localhost:8088/doc.html
     */

    // 配置Swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)  // 指定api类型位swagger2
                    .apiInfo(apiInfo())                 // 用于定义api文档的汇总信息
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.yuseven.controller"))    // 指定扫描的controller包
                    .paths(PathSelectors.any())         // 所有controller
                    .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("天天吃货 电商平台接口api")    // 文档页标题
                .contact(new Contact("yuseven","https://www.baidu.com","abc@imooc.com"))    // 联系人信息
                .description("专为天天吃货提供的api文档")  // 文档描述
                .version("1.0.1")   // 文档版本号
                .termsOfServiceUrl("https://www.baidu.com")  // 网站地址
                .build();
    }
}
