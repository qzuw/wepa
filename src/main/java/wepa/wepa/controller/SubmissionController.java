package wepa.wepa.controller;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wepa.wepa.domain.Course;
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

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping()
    public String listSubmissions(Model model) {
        //pagination later
        List<Submission> submissions = submissionRepository.findAll();
        model.addAttribute("submissions", submissions);
        return "submission/listSubmissions";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping("/{id}")
    public String showSubmission(Model model, @PathVariable Long id) {
        Submission submission = submissionRepository.findOne(id);
        model.addAttribute("submission", submission);
        return "submission/showSubmission";
    }

    @RequestMapping(value = "/courses/{idC}/week/{idW}", method = RequestMethod.POST)
    public String postExercises(@Valid @ModelAttribute SubmissionFormObject submissionFormObject,
            BindingResult bindingResult, @PathVariable Long idC, @PathVariable Integer idW,
            Model model) {

        Week week = weekRepository.findByCourseAndWeek(courseRepository.findOne(idC), idW);
        model.addAttribute("week", week);
        if (bindingResult.hasErrors()) {
            return "week/showWeek";
        }

        Person person = personFromSubmissionFormObject(submissionFormObject);

        Submission submission = submissionFromSubmissionFormObject(person, submissionFormObject, week);

        model.addAttribute("person", person);
        model.addAttribute("submission", submission);

        logSubmission(person);

        return "submission/submissionFormFilled";
    }

    private void logSubmission(Person person) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Person loggedIn = personRepository.findByName(auth.getName());

            logService.info("Person " + person.getName() + " (" + person.getStudentNumber() + ") submitted exercises.");
        }
    }

    private Submission submissionFromSubmissionFormObject(Person person, SubmissionFormObject submissionFormObject, Week week) {
        Submission submission = new Submission();
        submission.setStudent(person);
        submission.setExerciseCount(submissionFormObject.getExerciseCount());
        submission.setExerciseSubmission(submissionFormObject.getExerciseSubmission());
        submission.setWeek(week);
        submission.setSubmissionTime(new Date(System.currentTimeMillis()));
        submissionRepository.save(submission);
        return submission;
    }

    private Person personFromSubmissionFormObject(SubmissionFormObject submissionFormObject) {
        Person person = personRepository.findByStudentNumber(submissionFormObject.getStudentNumber());
        if (person == null) {
            person = new Person();
            person.setStudentNumber(submissionFormObject.getStudentNumber());
            person.setName(submissionFormObject.getName());
            personRepository.save(person);
        }
        return person;
    }
}
