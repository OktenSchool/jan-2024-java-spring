package org.okten.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@ResponseBody
//@RestController // @RestController = @Controller + @ResponseBody
public class BasicController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from basic controller";
    }
}
