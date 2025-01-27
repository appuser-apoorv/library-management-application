package com.example.library_management.Library.Management.Application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
@Bean
public OpenAPI customOpenApi(){

    return new OpenAPI()
            .info(new Info()
                    .title("Library Management Application")
                    .version("1.0")
                    .description("API documentation for Library Management System"))
            .components(new Components()
                .addSecuritySchemes("oauth2", new SecurityScheme()
                    .type(SecurityScheme.Type.OAUTH2)
                    .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                            .authorizationUrl("http://localhost:8080/realms/library-realm/protocol/openid-connect/auth")
                            .tokenUrl("http://localhost:8080/realms/library-realm/protocol/openid-connect/token")))))
            .addSecurityItem(new SecurityRequirement().addList("oauth2"));
}

}
