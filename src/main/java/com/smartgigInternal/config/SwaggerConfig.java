package com.smartgigInternal.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig  extends WebMvcConfigurationSupport {
	
	@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.smartgigInternal"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .globalOperationParameters(
            	        Arrays.asList(new ParameterBuilder()
            	                .name("Authorization")
            	                .modelRef(new ModelRef("string"))
            	                .parameterType("header")
            	                .defaultValue("Bearer ") 
            	                .required(false)
            	                .build(),
            	        new ParameterBuilder()
		                .name("X-Platform")
		                .modelRef(new ModelRef("string"))
		                .parameterType("header")
		                .required(false)
		                .build(),
		                new ParameterBuilder()
		                .name("isMultiSessionRequired")
		                .modelRef(new ModelRef("Boolean"))
		                .parameterType("header")
		                .required(false)
		                .build()
            	        ));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Smartgig Internal Application")
                .description("Smartgig Internal Application")
                .version("v1.0")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}