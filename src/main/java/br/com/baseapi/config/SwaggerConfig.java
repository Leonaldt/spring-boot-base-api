package br.com.baseapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${spring.profiles.active}")
    private String enviroment;

    @Value("${baseapi.auth-server}")
    private String server;

    @Value("${baseapi.scope.read}")
    private String read;

    @Value("${baseapi.scope.write}")
    private String write;

    @Value("${baseapi.scope.trust}")
    private String trust;

    @Value("${baseapi.client.id}")
    private String clientId;

    @Value("${baseapi.client.secret}")
    private String clientSecret;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.baseapi"))
                .paths(PathSelectors.any()).build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Base API-" + enviroment.toUpperCase(),
                "Base API",
                "API 1.0",
                "Terms of service",
                new Contact("Company Here", "http://www.site.com.br/", "naoresponda@email.org.br"),
                "License of API", "API license URL", Collections.emptyList());
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private OAuth securityScheme() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope(read, "read all"));
        authorizationScopeList.add(new AuthorizationScope(write, "trust all"));
        authorizationScopeList.add(new AuthorizationScope(trust, "access all"));

        List<GrantType> grantTypes = new ArrayList();

        grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(server + "/oauth/token"));

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope(read, "for read operations"),
                new AuthorizationScope(write, "for write operations"),
                new AuthorizationScope(trust, "trust all")};
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Collections.singletonList(new SecurityReference("oauth2schema", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

}
