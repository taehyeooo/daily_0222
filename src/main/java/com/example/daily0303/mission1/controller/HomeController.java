package com.example.daily0303.mission1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/board")
    public String board() {
        return "board";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
