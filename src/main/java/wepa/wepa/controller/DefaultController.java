/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.PersonRepository;

@Controller
public class DefaultController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    public void init() {

        List<Person> users = personRepository.findAll();
        if (users.isEmpty()) {
            Person user = new Person();
            user.setName("teach");
            user.setStudentNumber("000000000");
            user.setPassword(passwordEncoder.encode("teach"));
            user.setAuthorities(Arrays.asList("TEACHER"));
            personRepository.save(user);
        }
    }

    @RequestMapping("*")
    public String handleDefault() {
        return "index";
    }
}
