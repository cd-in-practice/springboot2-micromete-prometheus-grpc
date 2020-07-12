package codes.showme.demo.springboot2micrometer.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Timed()
public class HttpController {

    @GetMapping("/hello")
    String hello() {
        return "hello";
    }
}
