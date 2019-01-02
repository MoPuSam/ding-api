package com.oulam.dingding;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@Configuration
//自动引入当前包下的service,component....
@ComponentScan("com.oulam")//扫描 @Controller、@Service 注解；
@SpringBootApplication
@EnableScheduling   //启动定时任务
@MapperScan("com.oulam.dingding.dao")//将项目中对应的mapper类的路径加进来就可以了
@EntityScan(basePackages = "com.oulam.dingding.bean")//扫描 @Entity 注解；
public class DingmessageApplication {
	private static final Logger log = LoggerFactory.getLogger(DingmessageApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DingmessageApplication.class, args);
	}
}
