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
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Log;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.LogRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.service.PersonService;

@Controller
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private PersonService personService;

    @ModelAttribute
    private Person getPerson() {
        return new Person();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getPersons() {
        return "redirect:/persons/page/1";
    }
    
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public String getPersons(@PathVariable Integer pageNumber, Model model) {
        if (pageNumber < 1) {
            return "redirect:/persons/page/1";
        }
        Page<Person> page = personService.getPersonPage(pageNumber - 1);
        int current = page.getNumber() + 1;
        int previous = current - 1;
        int next = current + 1;
        if (previous >= 1) {
            model.addAttribute("previous", previous);
        }

        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        
        if (next <= end) {
            model.addAttribute("next", next);
        }

        model.addAttribute("personLog", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        List<Person> persons = page.getContent();

        model.addAttribute("persons", persons);
        return "person/personList";

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getOnePerson(Model model, @PathVariable Long id) {
        model.addAttribute("person", personRepository.findOne(id));
        return "person/personInfo";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPersonForm() {
        return "person/addperson";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String postPerson(Model model,
            @RequestParam String studentNumber,
            @RequestParam String name,
            @Valid @ModelAttribute Person addedPerson,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "person/addperson";
        }

        Person person = addedPerson;
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

        return "redirect:/persons";
    }
}
