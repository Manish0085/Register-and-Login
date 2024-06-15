
package com.SpringSecurity.Controller;//package com.example.SpringSecurity.Controller;
import com.SpringSecurity.Service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Controller
public class WelcomeController {


    @Autowired
    private CustomUserService customUserService;


    @GetMapping("/welcome")
    public String getWelcome() {
        return "welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Set<String> roles) {

          customUserService.registerUser(name, email, username, password, roles);
          return "redirect:/welcome";
    }
}

