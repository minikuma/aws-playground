package me.minikuma.bean.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigA {
    @Bean
    public BeanA beanA() {
        return new BeanA();
    }
}
