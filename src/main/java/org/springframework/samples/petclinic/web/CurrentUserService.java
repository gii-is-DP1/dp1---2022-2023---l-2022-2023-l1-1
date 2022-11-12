package org.springframework.samples.petclinic.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    
    public String showCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == "anonymousUser") {
        	return "anonymous";
        } else {
        	 User currentUser = (User) authentication.getPrincipal();
             String username = currentUser.getUsername();
             
             return username;
        }
    }
}
