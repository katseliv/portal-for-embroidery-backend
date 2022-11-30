package ru.vsu.portalforembroidery.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    /**
     * Swagger URL:
     * 1. http://localhost:8080/swagger-ui/
     * 2. http://localhost:8080/swagger-ui/index.html
     * 3. http://localhost:8080/v2/api-docs
     **/

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.vsu.portalforembroidery.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Portal For Embroidery API")
                .description("FCS Diploma Project.")
                .contact(new Contact("Ekaterina Selivanova", "https://github.com/katseliv", "selivanova_e_a@sc.vsu.ru"))
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Bearer", authorizationScopes));
    }

}
