package ru.iarmoshenko.NauJava.config;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.iarmoshenko.NauJava.entity.LegacyPassword;

@Configuration
public class Config {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.version}")
    private String appVersion;

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<LegacyPassword> passwordContainer()
    {
        return new ArrayList<>();
    }

    @PostConstruct
    public void printAppInfo() {
        System.out.printf("Приложение: %s | Версия: %s%n", appName, appVersion);
        System.out.println("Spring Boot Actuator доступен: http://localhost:8080/actuator");
    }
}