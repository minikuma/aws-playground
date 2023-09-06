package me.minikuma.bean;

import lombok.extern.slf4j.Slf4j;
import me.minikuma.bean.code.BeanA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BeanTest {

    @Autowired
    BeanA beanA;

    @Test
    void sameBeanCheck() {
        log.info("#1 beanA = {}", beanA.toString());
        log.info("#2 beanA = {}", beanA.toString());
    }
}
