package wepa.wepa.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import wepa.wepa.domain.Password;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.service.HelperService;
import wepa.wepa.service.LogService;
import wepa.wepa.service.PersonService;

@Controller
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private LogService logService;

    @Autowired
    private HelperService paginationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute
    private Person getPerson() {
        return new Person();
    }

    @ModelAttribute
    private Password getPassword() {
        return new Password();
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

        Person loggedIn = personRepository.findByName(auth.getName());

        if (loggedIn != null) {

            logService.info("Person \"" + person.getName() + "\" was added by " + loggedIn.getName() + " (" + loggedIn.getId() + ")");
        } else {
            logService.info("Person \"" + person.getName() + "\" was added.");
        }

        return "redirect:/persons";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/{id}/passwd", method = RequestMethod.GET)
    public String showPasswdForm(Model model, @PathVariable Long id) {
        Person person = personRepository.findOne(id);
        model.addAttribute("person", person);
        return "person/passwd";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/{id}/passwd", method = RequestMethod.POST)
    public String postPasswd(Model model,
            @PathVariable Long id,
            @Valid @ModelAttribute Password passwd,
            BindingResult bindingResult) {

        Person person = personRepository.findOne(id);
        model.addAttribute("person", person);
        if (bindingResult.hasErrors()) {
            return "person/passwd";
        }

        person.setPassword(passwordEncoder.encode(passwd.getPassword()));
        personRepository.save(person);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Person loggedIn = personRepository.findByName(auth.getName());

        if (loggedIn != null) {

            logService.info("Person \"" + person.getName() + "\" was added by " + loggedIn.getName() + " (" + loggedIn.getId() + ")");
        } else {
            logService.info("Person \"" + person.getName() + "\" was added.");
        }

        return "redirect:/persons/" + person.getId();
    }

}
