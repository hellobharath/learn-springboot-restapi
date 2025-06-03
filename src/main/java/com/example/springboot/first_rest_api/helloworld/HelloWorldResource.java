package com.example.springboot.first_rest_api.helloworld;

import org.springframework.web.bind.annotation.PathVariable;
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

    // need to add a custom value and return it
    // ex: /hello-world-path-param/Ram will give json response as "Hello World, Ram"
    // {name} -> Path param, @PathVariable String name -> path variable
    @RequestMapping("hello-world-path-param/{name}")
    public HelloWorldBean helloWorldBeanPathParam(@PathVariable String name) {
        return new HelloWorldBean("Hello world, "+name);
    }

    // Ex: /hello-world-path-param/Hanuman/message/Jai Shree Ram
    @RequestMapping("hello-world-path-param/{name}/message/{message}")
    public HelloWorldBean helloWorldBeanMultiPathParam(@PathVariable String name,
                                                       @PathVariable String message) {
        return new HelloWorldBean("Hello world, "+name+", Message passed: "+message);
    }

}
