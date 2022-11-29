package com.customfusionrestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.customfusionrestapi.interceptor.RelianceSecurityInterceptor;

@Configuration
@ComponentScan({ "com.customfusionrestapi.interceptor" })
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	RelianceSecurityInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(interceptor).addPathPatterns("/luciad/**");

	}
}
