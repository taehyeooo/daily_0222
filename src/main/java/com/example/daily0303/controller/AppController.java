package com.example.daily0303.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/product/list")
    public String productList() {
        return "product/list";
    }

    @GetMapping("/product/add")
    public String productAdd() {
        return "product/add";
    }

    @GetMapping("/member/list")
    public String memberList() {
        return "member/list";
    }

    @GetMapping("/member/detail")
    public String memberDetail() {
        return "member/detail";
    }
}
