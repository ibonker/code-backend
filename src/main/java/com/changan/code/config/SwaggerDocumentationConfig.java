package com.changan.code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
    date = "2017-06-06T15:54:12.883+08:00")

@Configuration
@EnableSwagger2
public class SwaggerDocumentationConfig {

  ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("TITAN CODE API")
        .description(
            "TitanOTP代码生成器项目")
        .license("").licenseUrl("http://unlicense.org").termsOfServiceUrl("").version("1.0.0")
        .contact(new Contact("", "", "")).build();
  }

  @Bean
  public Docket customImplementation() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.changan.code.controller")).build()
        .apiInfo(apiInfo());
  }

}
