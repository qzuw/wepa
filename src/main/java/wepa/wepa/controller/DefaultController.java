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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wepa.wepa.Wepa;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.PersonRepository;

@Controller
public class DefaultController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CourseRepository courseRepository;

    static Logger log = Logger.getLogger(Wepa.class.getName());
    
    @PostConstruct
    public void init() {

        List<Person> users = personRepository.findAll();
        if (users.isEmpty()) {
            Person user = new Person();
            user.setName("Teacher");
            user.setStudentNumber("000000000");
            user.setPassword(passwordEncoder.encode("teach"));
            user.setAuthorities(Arrays.asList("TEACHER"));
            personRepository.save(user);
            log.info("No users found. Creating default user.");
        }
    }

    @RequestMapping("*")
    public String handleDefault(Model model) {
        model.addAttribute("currentCourses", courseRepository.findCurrentCourses(new Date(System.currentTimeMillis())));
        return "index";
    }
}
