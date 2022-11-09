package ru.vsu.portalforembroidery.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {
        "ru.vsu.portalforembroidery.model.entity",
        "ru.vsu.portalforembroidery.converter"
})
@EnableJpaRepositories(basePackages = "ru.vsu.portalforembroidery.repository")
public class DatabaseConfiguration {
}
