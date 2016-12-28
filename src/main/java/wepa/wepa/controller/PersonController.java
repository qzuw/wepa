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
import org.springframework.security.access.annotation.Secured;
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
import wepa.wepa.service.HelperService;
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

    @Autowired
    private HelperService paginationService;

    @ModelAttribute
    private Person getPerson() {
        return new Person();
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(method = RequestMethod.GET)
    public String getPersons() {
        return "redirect:/persons/page/1";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public String getPersons(@PathVariable Integer pageNumber, Model model) {
        if (pageNumber < 1) {
            return "redirect:/persons/page/1";
        }
        Page<Person> page = personService.getPersonPage(pageNumber - 1);

        model = paginationService.countPagination(page.getNumber(), page.getTotalPages(), model, "persons");

        List<Person> persons = page.getContent();

        model.addAttribute("persons", persons);
        return "person/personList";

    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getOnePerson(Model model, @PathVariable Long id) {
        model.addAttribute("person", personRepository.findOne(id));
        return "person/personInfo";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPersonForm() {
        return "person/addperson";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
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
