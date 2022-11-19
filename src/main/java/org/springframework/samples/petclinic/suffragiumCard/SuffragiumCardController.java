package org.springframework.samples.petclinic.suffragiumCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/suffragium")
public class SuffragiumCardController {

    @Autowired
    private SuffragiumCardService suffragiumCardService;

    public SuffragiumCardController(SuffragiumCardService service) {
        this.suffragiumCardService = service;
    }
    
}
