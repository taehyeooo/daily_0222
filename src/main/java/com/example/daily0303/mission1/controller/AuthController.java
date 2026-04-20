package com.example.daily0303.mission1.controller;

import com.example.daily0303.mission1.dto.SignupRequestDto;
import com.example.daily0303.mission1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupRequestDto", new SignupRequestDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(SignupRequestDto dto, Model model) {
        try {
            userService.signup(dto);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }
}
