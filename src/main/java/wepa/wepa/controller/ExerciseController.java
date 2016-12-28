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

    @ModelAttribute
    private Exercise getExercise() {
        return new Exercise();
    }
    
    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/{idE}/edit", method = RequestMethod.GET)
    public String showExerciseEdit(Model model, @PathVariable Long idE) {
        Exercise exercise = exerciseRepository.findOne(idE);
        model.addAttribute("exercise", exercise);
        model.addAttribute("id", idE);
        return "exercise/modifyExercise";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/{idE}/edit", method = RequestMethod.POST)
    public String editExercise(@PathVariable Long idE, @Valid @ModelAttribute Exercise exercise, BindingResult bindingResult) {
        Exercise oldExercise = exerciseRepository.findOne(idE);
        oldExercise.setDescription(exercise.getDescription());
        exercise.setId(oldExercise.getId()); //saattaa sotkea???
        if (bindingResult.hasErrors()) {
            return "exercise/modifyExercise";
        }
        
        exerciseRepository.save(oldExercise);
        return "redirect:/courses/"+ oldExercise.getWeek().getCourse().getId() + "/week/" + oldExercise.getWeek().getWeek();
    } 

}