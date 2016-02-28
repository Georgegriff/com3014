package com.com3014.group1.projectmatching.rest;

import com.com3014.group1.projectmatching.core.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestHelloService {
    
    @Autowired
    private HelloService hello;
    
     @RequestMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return hello.sayHello(name);
    }
}
