package com.tenminread.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("tenminread API")
        .description("tenminread 백엔드 API 명세서")
        .version("v1.0.0")
        .contact(new Contact()
          .name("tenminread Team")
          .email("contact@tenminread.example")))
      .servers(List.of(
        new Server().url("http://localhost:8080").description("로컬 개발 서버")
      ));
  }
}
