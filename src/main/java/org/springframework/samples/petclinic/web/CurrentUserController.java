package org.springframework.samples.petclinic.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrentUserController {
    
    @GetMapping("/currentUser")
    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if(auth.isAuthenticated() && auth.getPrincipal() instanceof User){
                User currentUser = (User) auth.getPrincipal();
                return currentUser.getUsername();
            } 
        }
        return "anonymus";
    }
}
