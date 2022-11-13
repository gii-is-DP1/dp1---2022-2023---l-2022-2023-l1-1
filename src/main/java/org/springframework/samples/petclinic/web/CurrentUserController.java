package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrentUserController {

    private CurrentUserService currentUserService;

    @Autowired
    public CurrentUserController(CurrentUserService cUS) {
        this.currentUserService = cUS;
    }
    
    @GetMapping("/currentuser")
    public String getCurrentUser() {
        return currentUserService.getCurrentUser();
    }
}
