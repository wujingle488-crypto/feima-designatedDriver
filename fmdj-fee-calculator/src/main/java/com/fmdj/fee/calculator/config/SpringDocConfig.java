package com.fmdj.fee.calculator.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "fmdj-fee-calculator",
                description = "飞码代驾计费子系统",
                version = "1.0"
        )
)
@Configuration
public class SpringDocConfig {
}