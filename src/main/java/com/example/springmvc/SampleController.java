package com.example.springmvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    // preHandle 1
    // preHandle 2
    // 요청 처리
    // postHandle 2 *
    // postHandle 1 *
    // 뷰 렌더링
    // afterCompletion 2 *
    // afterCompletion 1 *

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") Person person) {
        return "hello " + person.getName();
    }

    @GetMapping("/hello")
    public String hello2(@RequestParam("id") Person person) {
        System.out.println(person.getId());
        System.out.println(person.getName());
        return "hello " + person.getName();
    }
}
