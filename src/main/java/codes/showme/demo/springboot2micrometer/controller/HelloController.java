package codes.showme.demo.springboot2micrometer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class HelloController {

    @GetMapping("/hello")
    String hello() {
        return "hello";
    }
}
