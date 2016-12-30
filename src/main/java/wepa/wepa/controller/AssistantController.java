package wepa.wepa.controller;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.service.HelperService;
import wepa.wepa.service.LogService;
import wepa.wepa.service.PersonService;

@Controller
@RequestMapping("/assistants")
public class AssistantController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private LogService logService;

    @Autowired
    private HelperService paginationService;

    @ModelAttribute
    private Person getPerson() {
        return new Person();
    }

    @RequestMapping()
    public String defaultAssistantPage() {
        return "redirect:/";
    }

    @Secured({"ROLE_TEACHER"})
    @RequestMapping(value = "/courses/{courseId}/add", method = RequestMethod.POST)
    public String addAssistant(Model model, @PathVariable Long courseId,
            @Valid @ModelAttribute Person assistant, BindingResult bindingResult) {

        Course course = courseRepository.findOne(courseId);
        if (course == null) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course);

        if (bindingResult.hasErrors()) {
            return "course/showCourse";
        }

        Person person = personRepository.findByStudentNumber(assistant.getStudentNumber());
        if (person != null) {

            List<Person> assistants = course.getAssistants();
            assistants.add(person);
            course.setAssistants(assistants);
            courseRepository.save(course);

            List<Course> courses = person.getCoursesAssisted();
            courses.add(course);
            person.setCoursesAssisted(courses);
            List<String> authorities = person.getAuthorities();
            authorities.add("ASSISTANT");
            person.setAuthorities(authorities);
            personRepository.save(person);

            logAssistantChange("added", person, course);

            return "redirect:/courses/" + course.getId();
        }

        return "redirect:/courses";
    }

    @Secured({"ROLE_TEACHER"})
    @RequestMapping(value = "/courses/{courseId}/remove/{personId}", method = RequestMethod.POST)
    public String removeAssistant(@PathVariable Long courseId, @PathVariable Long personId) {

        Person person = personRepository.findOne(personId);
        Course course = courseRepository.findOne(courseId);
        if (person != null && course != null) {

            List<Person> assistants = course.getAssistants();
            assistants.remove(person);
            course.setAssistants(assistants);
            courseRepository.save(course);

            List<Course> courses = person.getCoursesAssisted();
            courses.remove(course);
            person.setCoursesAssisted(courses);
            if (courses.isEmpty()) {
                List<String> authorities = person.getAuthorities();
                authorities.remove("ASSISTANT");
                person.setAuthorities(authorities);
            }
            personRepository.save(person);

            logAssistantChange("removed", person, course);

            return "redirect:/courses/" + course.getId();
        }

        return "redirect:/courses";
    }

    private void logAssistantChange(String replace, Person person, Course course) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Person loggedIn = personRepository.findByName(auth.getName());

            logService.info("Person \"" + person.getName() + "\" was " + replace + " by " + loggedIn.getName() + " (" + loggedIn.getId() + ") as assistant on course " + course.getName());
        } else {
            logService.info("Person \"" + person.getName() + "\" was " + replace + " as assistant on course " + course.getName() + ".");
        }
    }

}
