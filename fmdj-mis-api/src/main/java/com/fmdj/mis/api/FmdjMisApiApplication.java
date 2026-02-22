package com.fmdj.mis.api;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
@MapperScan("com.fmdj.mis.api.db.dao")
@ComponentScan("com.fmdj.*")
@EnableDistributedTransaction
public class FmdjMisApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmdjMisApiApplication.class, args);
    }

}
