package com.example.springboot.first_rest_api.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldResource {
    // hello-world => "Hello world"

    @RequestMapping("/hello-world")
    @ResponseBody
    public String helloWorld() {
        return "Hello world";
    }

}
