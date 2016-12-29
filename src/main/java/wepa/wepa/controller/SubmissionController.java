package wepa.wepa.controller;

import java.util.Date;
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
import wepa.wepa.domain.SubmissionFormObject;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.Submission;
import wepa.wepa.domain.Week;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.SubmissionRepository;
import wepa.wepa.repository.WeekRepository;
import wepa.wepa.service.LogService;

@Controller
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LogService logService;

    @ModelAttribute
    private SubmissionFormObject getFormObject() {
        return new SubmissionFormObject();
    }

    @RequestMapping()
    public String handleDefault() {
        return "week/showWeek";
    }

    @RequestMapping("/{id}")
    public String showSubmission(Model model, @PathVariable Long id) {
        Submission submission = submissionRepository.findOne(id);
        model.addAttribute("submission", submission);
        return "submission/showSubmission";
    }

    @RequestMapping(value = "/courses/{idC}/week/{idW}", method = RequestMethod.POST)
    public String postExercises(@Valid @ModelAttribute SubmissionFormObject submissionFormObject,
            BindingResult bindingResult,
            Model model,
            @PathVariable Long idC,
            @PathVariable Integer idW) {
        Week week = weekRepository.findByCourseAndWeek(courseRepository.findOne(idC), idW);
        model.addAttribute("week", week);
        if (bindingResult.hasErrors()) {
            return "week/showWeek";
        }

        Person person = personRepository.findByStudentNumber(submissionFormObject.getStudentNumber());
        if (person == null) {
            person = new Person();
            person.setStudentNumber(submissionFormObject.getStudentNumber());
            person.setName(submissionFormObject.getName());
            personRepository.save(person);
        }

        Submission submission = new Submission();

        submission.setStudent(person);
        submission.setExerciseCount(submissionFormObject.getExerciseCount());
        submission.setExerciseSubmission(submissionFormObject.getExerciseSubmission());
        submission.setWeek(week);

        submissionRepository.save(submission);

        model.addAttribute("person", person);
        model.addAttribute("submission", submission);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Person loggedIn = personRepository.findByName(auth.getName());

            logService.info("Person " + person.getName() + " (" + person.getStudentNumber() + ") submitted exercises.");
        }

        return "submission/submissionFormFilled";
    }
}
