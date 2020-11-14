package com.jacobwebb.restfulwebservices.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "com.jacobwebb.resetfulwebservices.task" })
public class SpringTaskConfig {

}
