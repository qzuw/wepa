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

    @Secured({"ROLE_TEACHER"})
    @RequestMapping(value = "/course/{courseId}/add/{personId}", method = RequestMethod.POST)
    public String addAssistant(@PathVariable Long courseId, @PathVariable Long personId) {

        Person person = personRepository.findOne(personId);
        Course course = courseRepository.findOne(courseId);
        if (person != null && course != null) {
            List<Course> courses = person.getCoursesAssisted();
            courses.add(course);
            person.setCoursesAssisted(courses);
            List<String> authorities = person.getAuthorities();
            authorities.add("ASSISTANT");
            person.setAuthorities(authorities);
            personRepository.save(person);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {
                Person loggedIn = personRepository.findByName(auth.getName());

                logService.info("Person \"" + person.getName() + "\" was added by " + loggedIn.getName() + " (" + loggedIn.getId() + ") as assistant on course " + course.getName());
            } else {
                logService.info("Person \"" + person.getName() + "\" was added as assistant on course " + course.getName() + ".");
            }

            return "redirect:/courses/" + course.getId();
        }

        return "redirect:/courses";
    }

    @Secured({"ROLE_TEACHER"})
    @RequestMapping(value = "/course/{courseId}/delete/{personId}", method = RequestMethod.DELETE)
    public String removeAssistant(@PathVariable Long courseId, @PathVariable Long personId) {

        Person person = personRepository.findOne(personId);
        Course course = courseRepository.findOne(courseId);
        if (person != null && course != null) {
            List<Course> courses = person.getCoursesAssisted();
            courses.remove(course);
            person.setCoursesAssisted(courses);
            if (courses.isEmpty()) {
                List<String> authorities = person.getAuthorities();
                authorities.remove("ASSISTANT");
                person.setAuthorities(authorities);
            }
            personRepository.save(person);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {
                Person loggedIn = personRepository.findByName(auth.getName());

                logService.info("Person \"" + person.getName() + "\" was removed by " + loggedIn.getName() + " (" + loggedIn.getId() + ") as assistant on course " + course.getName());
            } else {
                logService.info("Person \"" + person.getName() + "\" was removed as assistant on course " + course.getName() + ".");
            }

            return "redirect:/courses/" + course.getId();
        }

        return "redirect:/courses";
    }

}
