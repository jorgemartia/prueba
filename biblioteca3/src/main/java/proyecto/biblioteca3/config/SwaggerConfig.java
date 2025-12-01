package proyecto.biblioteca3.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi bibliotecaApi() {
        return GroupedOpenApi.builder()
                .group("biblioteca")
                .addOpenApiCustomizer(openApi -> openApi.info(
                        new Info()
                                .title("API Biblioteca")
                                .version("1.0.0")
                                .description("Sistema de Gestión de Biblioteca")
                ))
                .build();
    }
}
