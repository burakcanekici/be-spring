package com.be.bedemo.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String addDefaultMessage(String name){
        final String defaultMessage = "Hello " + name + ", nice to meet you!";
        return defaultMessage;
    }

    public Boolean checkMessage(String name){
        final String defaultMessage = "burakcan";
        return name.equals(defaultMessage);
    }
}
