package com.example.springboot.first_rest_api.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldResource {
    // hello-world => "Hello world"

    private MessageSource messageSource;

    public HelloWorldResource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default message", locale);

        /**
         * Steps for internationalization:
         * 1. Defining the values for different languages -> done using messages.properties
         * 2. Write the code to pick those values
         */
    }

}
