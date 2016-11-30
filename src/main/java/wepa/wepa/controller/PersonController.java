/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.PersonRepository;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    public void init() {

        Person user = new Person();
        user.setName("Nakki");
        user.setStudentNumber("0000");

        user = personRepository.save(user);

        Person user1 = new Person();
        user1.setName("Makkara");
        user1.setStudentNumber("1111");

        user1 = personRepository.save(user1);
    }

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String getPersons(Model model) {

        List<Person> persons = personRepository.findAll();

        model.addAttribute("persons", persons);
        return "personList";
    }

    @RequestMapping("/persons/{id}")
    public String getOnePerson(Model model, @PathVariable Long id) {
        model.addAttribute("person", personRepository.findOne(id));
        return "personInfo";
    }

}
