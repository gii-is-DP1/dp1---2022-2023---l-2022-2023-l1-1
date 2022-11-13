package org.springframework.samples.petclinic.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    
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
