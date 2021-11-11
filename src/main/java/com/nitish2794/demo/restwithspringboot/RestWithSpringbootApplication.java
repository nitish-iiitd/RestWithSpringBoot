package com.nitish2794.demo.restwithspringboot;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.UnknownHostException;

@SpringBootApplication
public class RestWithSpringbootApplication {

    @Autowired
    Environment environment;

    @Bean
    public OpenAPI springShopOpenAPI() throws UnknownHostException {
        return new OpenAPI()
                .info(new Info().title("be RESTful - Example API")
                        .description("Learn core concepts behind REST by exploring through this example")
                        .version("v0.0.1")
                        .license(new License()))
                        .externalDocs(new ExternalDocumentation()
                        .description("be RESTful Homepage")
                        .url("/"));
    }

    public static void main(String[] args) {
        SpringApplication.run(RestWithSpringbootApplication.class, args);
    }

}
