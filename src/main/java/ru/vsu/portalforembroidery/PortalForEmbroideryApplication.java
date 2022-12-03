package ru.vsu.portalforembroidery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:constants.properties")
@SpringBootApplication
public class PortalForEmbroideryApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(PortalForEmbroideryApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
