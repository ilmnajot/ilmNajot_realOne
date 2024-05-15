package com.example.ilmnajot.security.config;

import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@Configuration
//@OpenAPIDefinition(
//        info = @Info(contact = @Contact(
//                name = "ilmnajot.uz",
//                url = "https://sampm.uz",
//                email = "ilmnajot2021@gmail.com"
//        ),
//                title = "Samarkand Presidential School - Elbek Umar",
//                version = "1.0",
//                license = @License(
//                        name = "MIT License",
//                        url = "https://apache.org/mit/mitLicense"
//                ),
//                termsOfService = "Terms of Service"
//        ),
//        servers = {
//                @Server(
//                        description = "ilmNajot online platform",
//                        url = "https://localhost:8080"
//                )
//        }
//
//)
//@SecurityScheme(
//        name = "Bearer",
//        description = "Bearer token description here",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {

//    @Value("${openapi.samps-url}")
    private String url;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("www.ilmnajot.uz");
        server.setDescription("Open API documentation - SAMPS");

        Contact contact = new Contact()

                .email("samps@gmail.com")
                .name("SamPS library")
                .url("https://samps.uz");


        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Open API documentation - SAMPS library")
                .version("1.1")
                .contact(contact)
                .description("Open API documentation - SAMPS library")
                .termsOfService("https://wwww.brainone.com/terms");
//                .license(license);
        return new OpenAPI()
//                .info(info)
                .servers(List.of(server));
    }


}
