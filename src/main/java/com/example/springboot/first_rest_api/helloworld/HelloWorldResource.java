package com.example.springboot.first_rest_api.helloworld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {
    // hello-world => "Hello world"

    @RequestMapping("/hello-world")
    public String helloWorld() {
        return "Hello world";
    }

    @RequestMapping("/hello-world-bean")
    // This bean is returned as a JSON object using jackson
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello world");
    }

}
