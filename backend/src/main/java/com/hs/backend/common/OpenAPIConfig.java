package com.hs.backend.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("FBI Most Wanted API")
                        .description("API for accessing FBI Most Wanted information with caching")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Sielei Herman")
                                .email("hsielei@gmail.com")));
    }
}
