package wepa.wepa.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.StudentExercise;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.StudentExerciseRepository;

@Controller
public class ExerciseController {

    @Autowired
    private StudentExerciseRepository studentExerciseRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/exercises")
    public String handleDefault() {
        return "studentexercise/fillExercises";
    }

    @RequestMapping(value = "/exercises", method = RequestMethod.POST)
    public String postExercises(@RequestParam String studentNumber, @RequestParam String name, @RequestParam Integer exercises, Model model) {
        Person person = personRepository.findByName(name);
        if (person == null) {
            person = new Person();
            person.setStudentNumber(studentNumber);
            person.setName(name);
            personRepository.save(person);
        }

        StudentExercise studentExercise = new StudentExercise();

        studentExercise.setStudent(person);
        studentExercise.setExerciseCount(exercises);

        studentExerciseRepository.save(studentExercise);
        model.addAttribute("person", person);
        model.addAttribute("studentExercise", studentExercise);
        return "studentexercise/exercisesFormFilled";
    }
}
