package com.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/")
    public String hello() {
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password, Model model,
                               HttpServletRequest request) {
        // Here you would typically perform some kind of authentication logic
        // to check whether the username and password are valid.

        // For the sake of this example, we'll assume the authentication succeeded
        // and store the username in the session.
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        model.addAttribute("username", username);
        // Redirect to the home page or some other protected resource.
        return "redirect:/home";
    }
}
