package cn.bdqn.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
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
 * Swagger2配置
 * EnableSwagger2:开启Swagger自定义接口文档
 *
 * @author SwayJike
 * @date 2021/1/21
 */
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Configuration
public class Swagger2Config {

    /**
     * 创建Docket对象
     *
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("jike-admin-system")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.bdqn.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API基础信息
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger2-API接口文档")
                .description("API接口文档")
                .contact(new Contact("SwayJike", "http://www.baidu.com/", "848988457@qq.com"))
                .version("1.0.0")
                .build();
    }

}
