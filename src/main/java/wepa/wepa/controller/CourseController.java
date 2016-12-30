package wepa.wepa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import wepa.wepa.domain.Week;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.WeekRepository;
import wepa.wepa.service.CourseService;
import wepa.wepa.service.LogService;
import wepa.wepa.service.HelperService;
import wepa.wepa.service.PersonService;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LogService logService;

    @Autowired
    private PersonService personService;

    @Autowired
    private HelperService helperService;

    @ModelAttribute
    private Course getCourse() {
        return new Course();
    }

    @ModelAttribute
    private Person getPerson() {
        return new Person();
    }

    @RequestMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "course/listCourses";
    }

    @RequestMapping("/courses/{id}")
    public String showCourse(Model model, @PathVariable Long id) {
        if (false) {
            // redirect teacher/assistant
            return "redirect:/courses/" + id + "/page/1";
        }
        Course course = courseRepository.findOne(id);
        model.addAttribute("course", course);

        return "course/showCourse";
    }

    @RequestMapping(value = "/courses/{id}/addStudent", method = RequestMethod.POST)
    public String joinCourse(@PathVariable Long id,
            Model model,
            @Valid @ModelAttribute Person person,
            BindingResult bindingResult) {
        Course course = courseRepository.findOne(id);
        model.addAttribute("course", course);
        if (bindingResult.hasErrors()) {
            return "course/showCourse";
        }

        Person checkPerson = personRepository.findByStudentNumber(person.getStudentNumber());
        if (checkPerson == null) {
            person = personRepository.save(person);
        } else {
            person = checkPerson;
        }
        if (!course.getStudents().contains(person)) {
            course.getStudents().add(person);
            courseRepository.save(course);
        }
        return "redirect:/courses/" + id;
    }

    @RequestMapping("/courses/{id}/page/{pageNumber}")
    public String showCourse(Model model, @PathVariable Long id, @PathVariable Integer pageNumber) {
        if (pageNumber < 1) {
            return "redirect:/courses/" + id + "/page/1";
        }
        Course course = courseRepository.findOne(id);
        model.addAttribute("course", course);

        Page<Person> page = personService.findPageByCourse(pageNumber - 1, id);
        List<Person> students = page.getContent();
        model.addAttribute("students", students);

        model = helperService.countPagination(page.getNumber(), page.getTotalPages(), model, "courses/" + course.getId());

        return "course/showCourse";
    }

//    private void pagination(Page<Person> page, Model model, Course course) {
//        int current = page.getNumber() + 1;
//        int previous = current - 1;
//        int next = current + 1;
//        if (previous >= 1) {
//            model.addAttribute("previous", previous);
//        }
//
//        int begin = Math.max(1, current - 5);
//        int end = Math.min(begin + 10, page.getTotalPages());
//
//        if (next <= end) {
//            model.addAttribute("next", next);
//        }
//
//        model.addAttribute("personLog", page);
//        model.addAttribute("beginIndex", begin);
//        model.addAttribute("endIndex", end);
//        model.addAttribute("currentIndex", current);
//        model.addAttribute("pagePath", "courses/" + course.getId());
//    }
    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping("/courses/new")
    public String courseAddForm() {
        return "course/courseAddForm";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/courses/new", method = RequestMethod.POST)
    public String addCourse(Model model,
            @RequestParam Integer weeks,
            @ModelAttribute Course addedCourse,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "course/courseAddForm";
        }

        Course course = addedCourse;
        courseRepository.save(course);

//        List<Week> weeklist = generateWeekList(weeks, course);
        course.setWeeks(helperService.generateWeekList(weeks, course));

        courseRepository.save(course);

        logService.info("Course \"" + course.getName() + "\" was added.");

        model.addAttribute("course", addedCourse);

        return "redirect:/courses/" + course.getId();
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping("/courses/{id}/edit")
    public String courseEditForm(@PathVariable Long id, Model model) {
        Course course = courseRepository.findOne(id);
        model.addAttribute("course", course);
        return "course/courseEditForm";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ASSISTANT"})
    @RequestMapping(value = "/courses/{id}/edit", method = RequestMethod.POST)
    public String editCourse(@PathVariable Long id,
            Model model,
            @ModelAttribute Course editedCourse,
            BindingResult bindingResult) {

        editedCourse.setId(id);

        if (bindingResult.hasErrors()) {
            return "course/courseEditForm";
        }

        Course course = courseRepository.findOne(id);
        course.setName(editedCourse.getName());
        course.setCourseStart(editedCourse.getCourseStart());
        course.setCourseEnd(editedCourse.getCourseEnd());

        courseRepository.save(course);

        return "redirect:/courses/" + course.getId();
    }

}
