package br.com.alura.forum.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
//@OpenAPIDefinition(info = Info(title = "API fórum", description = "Backend desenvolvido no curso kotlin + springboot"))
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
)
class SwaggerConfiguration
{
    @Bean
    fun customOpenAPI(): OpenAPI {
        val swaggerInfo = Info()
            .title("Forum API")
            .description("API backend forum desenvolvida com kotlin + spring-boot")
            .version("SNAPSHOT-1.0.0")
        return OpenAPI().info(swaggerInfo)
    }
}