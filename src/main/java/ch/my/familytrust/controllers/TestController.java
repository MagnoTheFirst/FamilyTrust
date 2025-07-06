package ch.my.familytrust.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class TestController {

    @GetMapping("/public/hello")
    public String test(){
        return "Hello World";
    }


    @GetMapping("/hello")
    public String test1(){
        return "Hello without World";
    }
}
