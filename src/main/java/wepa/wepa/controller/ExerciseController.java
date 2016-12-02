package wepa.wepa.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.StudentExercise;
import wepa.wepa.repository.StudentExerciseRepository;

@Controller
public class ExerciseController {

    @Autowired
    private StudentExerciseRepository studentExerciseRepository;

    @RequestMapping("/exercises")
    public String handleDefault() {
        return "fillExercises";
    }

    @RequestMapping(value = "/exercises", method = RequestMethod.POST)
    public String postExercises(@RequestParam String studentNumber, @RequestParam String name, @RequestParam Integer exercises, Model model) {
        Person person = new Person();
        StudentExercise studentExercise = new StudentExercise();
        person.setStudentNumber(studentNumber);
        person.setName(name);
        studentExercise.setStudent(person);
        studentExercise.setExerciseCount(exercises);

        studentExerciseRepository.save(person);
        model.addAttribute("person", person );
        model.addAttribute("studentExercise", studentExercise);
        return "exercisesFormFilled";
    }
}
