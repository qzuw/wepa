/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Exercise;
import wepa.wepa.repository.ExerciseRepository;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @RolesAllowed({"TEACHER", "ASSISTANT"})
    @RequestMapping(value = "/{idE}/edit", method = RequestMethod.GET)
    public String showExerciseEdit(Model model, @PathVariable Long idE) {
        Exercise exercise = exerciseRepository.findOne(idE);
        model.addAttribute("exercise", exercise);
        return "exercise/modifyExercise";
    }

    @RolesAllowed({"TEACHER", "ASSISTANT"})
    @RequestMapping(value = "/{idE}/edit", method = RequestMethod.POST)
    public String editExercise(@PathVariable Long idE, @Valid @ModelAttribute Exercise exercise, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "exercise/modifyExercise";
        }

        Exercise exercise1 = exerciseRepository.findOne(idE);
        exercise1.setDescription(exercise.getDescription());
        exerciseRepository.save(exercise1);
        return "redirect:/courses/"+ exercise1.getWeek().getCourse().getId() + "/week/" + exercise1.getWeek().getId();
    } 

}
