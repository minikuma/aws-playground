package me.minikuma.bean.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanA {
    public BeanA() {
        ClassA classA = new ClassA();
        log.info("class A {}", classA.toString());
    }
}
