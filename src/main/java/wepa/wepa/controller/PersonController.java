/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Log;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.LogRepository;
import wepa.wepa.repository.PersonRepository;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LogRepository logRepository;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String getPersons(Model model) {

        List<Person> persons = personRepository.findAll();

        model.addAttribute("persons", persons);
        return "person/personList";
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    public String getOnePerson(Model model, @PathVariable Long id) {
        model.addAttribute("person", personRepository.findOne(id));
        return "person/personInfo";
    }

    @RequestMapping(value = "/addperson", method = RequestMethod.GET)
    public String addPersonForm() {
        return "person/addperson";
    }

    @RequestMapping(value = "/addperson", method = RequestMethod.POST)
    public String postPerson(Model model, @RequestParam String studentNumber, @RequestParam String name) {
        Person person = new Person();
        person.setName(name);
        person.setStudentNumber(studentNumber);
        person.setAuthorities(Arrays.asList("STUDENT"));
        personRepository.save(person);
        model.addAttribute("addedperson", person);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Person loggedIn = personRepository.findByName(auth.getName());

            Log log = new Log();

            log.setLogMessage("Person \"" + person.getName() + "\" was added.");
            log.setPerson(loggedIn);
            log.setDate(new Date(System.currentTimeMillis()));

            logRepository.save(log);
        }

        return "redirect:/addperson";
    }
}
