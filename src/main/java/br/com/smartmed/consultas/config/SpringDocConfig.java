package br.com.smartmed.consultas.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8080/api/swagger-ui/index.html
//http://localhost:8080/api/h2/login.jsp?jsessionid=6818bb1f2dbc860725c1214b9ffcf500

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        //SEM adição de Segurança - JWT
        return new OpenAPI()
                .info(new Info()
                        .title("API - Gerenciamento de Consultas Médicas")
                        .contact(new Contact()
                                .name("Equipe SmartMed")
                                .email("faleconosco@smartmed.com.br")
                                .url("smartmed.com.br/consultas"))
                        .description("Sistema de Gerenciamento de Consultas Médicas")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação")
                        .url("https://www.smartmed.com.br/consultas/docs/open-api"));
    }

/*
        //COM adição de Segurança - JWT
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP).scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("API - Gerenciamento de Consultas Médicas")
                        .contact(new Contact()
                                .name("Equipe SmartMed")
                                .email("faleconosco@smartmed.com.br")
                                .url("smartmed.com.br/consultas"))
                        .description("Sistema de Gerenciamento de Consultas Médicas")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação")
                        .url("https://www.smartmed.com.br/consultas/docs/open-api"));
    }
*/

}