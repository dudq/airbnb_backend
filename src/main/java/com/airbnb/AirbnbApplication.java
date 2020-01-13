package com.airbnb;

import com.airbnb.services.HostService;
import com.airbnb.services.impl.HostServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AirbnbApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AirbnbApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AirbnbApplication.class);
    }

    @Bean
    public HostService hostService() {
        return new HostServiceImpl();
    }
}
