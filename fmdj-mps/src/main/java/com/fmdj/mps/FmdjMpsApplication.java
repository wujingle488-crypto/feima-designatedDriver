package com.fmdj.mps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.fmdj.*")
public class FmdjMpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmdjMpsApplication.class, args);
    }

}
