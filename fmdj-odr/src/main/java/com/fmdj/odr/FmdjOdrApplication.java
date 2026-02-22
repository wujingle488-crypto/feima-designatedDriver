package com.fmdj.odr;
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
@MapperScan({"com.fmdj.odr.db.dao"})
@ComponentScan({"com.fmdj.*"})
@EnableDistributedTransaction
public class FmdjOdrApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmdjOdrApplication.class, args);
    }
}