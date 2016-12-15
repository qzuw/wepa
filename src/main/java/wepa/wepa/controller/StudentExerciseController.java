package wepa.wepa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.FormObject;
import wepa.wepa.domain.Log;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.StudentExercise;
import wepa.wepa.repository.LogRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.StudentExerciseRepository;

@Controller
public class StudentExerciseController {

    @Autowired
    private StudentExerciseRepository studentExerciseRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LogRepository logRepository;

    @ModelAttribute
    private FormObject getFormObject() {
        return new FormObject();
    }

    @RequestMapping("/exercises")
    public String handleDefault() {
        return "studentexercise/fillExercises";
    }

    @RequestMapping(value = "/exercises", method = RequestMethod.POST)
    public String postExercises(@Valid @ModelAttribute FormObject formObject, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "studentexercise/fillExercises";
        }

        Person person = personRepository.findByStudentNumber(formObject.getStudentNumber());
        if (person == null) {
            person = new Person();
            person.setStudentNumber(formObject.getStudentNumber());
            person.setName(formObject.getName());
            personRepository.save(person);
        }

        StudentExercise studentExercise = new StudentExercise();

        studentExercise.setStudent(person);
        studentExercise.setExerciseCount(formObject.getExerciseCount());

        studentExerciseRepository.save(studentExercise);
        
        model.addAttribute("person", person);
        model.addAttribute("studentExercise", studentExercise);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Person loggedIn = personRepository.findByName(auth.getName());

        Log log = new Log();

        log.setLogMessage("Person " + person.getName() + " added made exercises.");
        log.setPerson(loggedIn);
        log.setDate(new Date(System.currentTimeMillis()));

        logRepository.save(log);

        return "studentexercise/exercisesFormFilled";
    }
}
