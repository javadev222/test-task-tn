package com.test_task_tn.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Getter
public class ApplicationConfig {
    @Value( "${spring.servlet.multipart.location}" )
    private String location;
}