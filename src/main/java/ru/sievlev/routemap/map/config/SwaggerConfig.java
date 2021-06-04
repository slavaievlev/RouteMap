package ru.sievlev.routemap.map.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.sievlev.routemap.map.config.properties.SwaggerProperties;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.LinkedList;
import java.util.List;

@Configuration
@Primary
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer, SwaggerResourcesProvider {

    private static final String JSON_URL = "/v2/api-docs";

    private final List<String> serviceNames;

    public SwaggerConfig(SwaggerProperties swaggerProperties) {
        this.serviceNames = swaggerProperties.getServiceNames();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/documentation/swagger-ui.html", "/swagger-ui.html");
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new LinkedList<>();
        for (String serviceName : serviceNames) {
            resources.add(swaggerResource(serviceName, "/../" + serviceName + JSON_URL));
        }

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String url) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setUrl(url);
        return swaggerResource;
    }
}
