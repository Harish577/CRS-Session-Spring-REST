package com.customfusionrestapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.luciad.fusion.platform.TLfnFusionPlatformApplication;

@SpringBootApplication
public class RelianceApplication {

	static ConfigurableApplicationContext sConfigurableApplicationContext;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(TLfnFusionPlatformApplication.class);
		builder.profiles("fusion.single", "fusion.development");
		sConfigurableApplicationContext = builder.run(args);
	}

}
