package com.fmdj.vhr.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "fmdj-vhr",
                description = "飞马代驾代金券子系统",
                version = "1.0"
        )
)
@Configuration
public class SpringDocConfig {
}