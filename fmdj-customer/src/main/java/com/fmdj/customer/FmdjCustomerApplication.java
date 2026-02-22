package com.fmdj.customer;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.fmdj.*")
@EnableDistributedTransaction
public class FmdjCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmdjCustomerApplication.class, args);
    }

}