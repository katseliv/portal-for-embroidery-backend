package ru.vsu.portalforembroidery.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "ru.vsu.portalforembroidery.rest"
})
public class RestConfiguration {

}