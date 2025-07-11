package ch.my.familytrust.config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${springdoc.version}")
    String version;

    @Value("${springdoc.title}")
    String title;

    @Value("${springdoc.description}")
    String description;



    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()                .
                info(new Info().title(title)
                .version(version)
                .description(description)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                        .url("http://springdoc.org")));
    }

}
