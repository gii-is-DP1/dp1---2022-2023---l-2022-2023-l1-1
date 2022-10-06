package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    

			List<Person> persons = new ArrayList<Person>();
			Person p1 = new Person(), p2= new Person(), p3= new Person(), p4 = new Person();
			p1.setFirstName("Álvaro");
			p1.setLastName("González");
			persons.add(p1);
			p2.setFirstName("Miguel");
			p2.setLastName("Manzano");
			persons.add(p2);
			p3.setFirstName("María");
			p3.setLastName("Barrancos");
			persons.add(p3);
			p4.setFirstName("David");
			p4.setLastName("González");
			persons.add(p4);
			model.put("persons", persons);
			model.put("title", "My project");
			model.put("group", "L1-1");
	    return "welcome";
	  }
}
