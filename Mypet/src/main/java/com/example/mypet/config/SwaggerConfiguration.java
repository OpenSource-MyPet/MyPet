package com.example.mypet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI(){
        Server server = new Server();
        server.setUrl("/");

        Info info = new Info()
                .title("Mypet")
                .version("1.0.0")
                .description("this is for test of Mypet");
        return new OpenAPI()
                .info(info)
                .addServersItem(server);
    }
}
