/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import org.springframework.stereotype.Controller;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Log;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.SubmissionFormObject;
import wepa.wepa.domain.Week;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.LogRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.WeekRepository;

@Controller
public class WeekController {

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private PersonRepository personRepository;

    @ModelAttribute
    private SubmissionFormObject getFormObject() {
        return new SubmissionFormObject();
    }

    @RequestMapping("/courses/{idC}/week/{idW}")
    public String handleDefault(@PathVariable Long idC, @PathVariable Integer idW, Model model) {
        model.addAttribute("week", weekRepository.findByCourseAndWeek(courseRepository.findOne(idC), idW));
        return "week/showWeek";
    }

    @RequestMapping(value = "/courses/{idC}/week/{idW}/modifyWeek", method = RequestMethod.GET)
    public String modify(Model model, @PathVariable Long idC, @PathVariable Integer idW) {
        //model.addAttribute("week", weeklyExerciseRepository.findOne(idW));
        model.addAttribute("week", weekRepository.findByCourseAndWeek(courseRepository.findOne(idC), idW));

        return "week/modifyWeek";
    }

    @RequestMapping(value = "/courses/{idC}/week/{idW}/modifyWeek", method = RequestMethod.POST)
    public String modifyWeek(@RequestParam String description, Model model, @PathVariable Long idC, @PathVariable Integer idW) {

        Week week = weekRepository.findByCourseAndWeek(courseRepository.findOne(idC), idW);

        String oldDescription = week.getDescription();

        week.setDescription(description);

        weekRepository.save(week);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Person loggedIn = personRepository.findByName(auth.getName());

        Log log = new Log();

        log.setLogMessage("\"" + week.getCourse().getName() + "\"-course's week \""
                + week.getWeek() + "\" was modified. Old description: "
                + oldDescription + " New description: " + week.getDescription());
        log.setPerson(loggedIn);
        log.setDate(new Date(System.currentTimeMillis()));

        logRepository.save(log);
        return "week/modifyWeek";
    }

}
