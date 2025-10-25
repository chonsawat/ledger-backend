package com.example.app.demo.Welcome;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/welcome")
    public String getWelcome(Model theModel) {
        theModel.addAttribute("theTime", java.time.LocalTime.now());
        return "welcome/welcome";
    }
}
