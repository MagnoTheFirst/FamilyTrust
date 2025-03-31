package ch.my.familytrust.config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
/*
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Laneri Family Trust API")
                        .version("1.0")
                        .description("API für das Family Trust Backend"));
    }
 */

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()                .
                info(new Info().title("Laneri Finance App")
                .version("1.0")
                .description("This is a sample Foobar server created using springdocs - " +
                        "a library for OpenAPI 3 with spring boot.")
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                        .url("http://springdoc.org")));
    }

}
