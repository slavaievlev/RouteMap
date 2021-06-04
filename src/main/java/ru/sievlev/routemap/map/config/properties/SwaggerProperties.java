package ru.sievlev.routemap.map.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("routemap.swagger")
public class SwaggerProperties {

    private List<String> serviceNames;
}
