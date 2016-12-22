package wepa.wepa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Log;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.Week;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.LogRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.WeekRepository;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private LogRepository logRepository;
    
    @Autowired
    private WeekRepository weeklyExerciseRepository;

    @RequestMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "course/listCourses";
    }

    @RequestMapping("/courses/{id}")
    public String showCourse(Model model, @PathVariable Long id) {
        Course course = courseRepository.findOne(id);
        model.addAttribute("course", course);
        return "course/showCourse";
    }

    //@RolesAllowed({"TEACHER", "ASSISTANT"})
    @RequestMapping("/courses/new")
    public String courseAddForm() {
        return "course/courseAddForm";
    }

    //@RolesAllowed({"TEACHER", "ASSISTANT"})
    @RequestMapping(value = "/courses/new", method = RequestMethod.POST)
    public String addCourse(@RequestParam String name, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date courseStart, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date courseEnd, @RequestParam Integer weeks) {
        Course course = new Course();
        course.setName(name);
        course.setCourseStart(courseStart);
        course.setCourseEnd(courseEnd);
        courseRepository.save(course);
        List<Week> weeklist = new ArrayList<>();
        for (int i = 1; i <= weeks; i++) {
            Week we = new Week();
            we.setWeek(i);
            we.setCourse(course);
            weeklyExerciseRepository.save(we);
            weeklist.add(we);
        }
        course.setWeeks(weeklist);

        courseRepository.save(course);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        Person loggedIn = personRepository.findByName(auth.getName());

        Log log = new Log();
        
        log.setLogMessage("Course \"" + course.getName() + "\" was added.");
        log.setPerson(loggedIn);
        log.setDate(new Date(System.currentTimeMillis()));
        
        logRepository.save(log);
        
        return "redirect:/courses/" + course.getId();
    }

    //@PreAuthorize("hasRole('TEACHER')")
    @Secured("TEACHER")
    //@Secured("hasAnyRole{'ROLE_TEACHER', 'ROLE_ASSISTANT'}")
    //@RolesAllowed({"TEACHER", "ASSISTANT"})
    @RequestMapping("/courses/{id}/edit")
    public String courseEditForm(@PathVariable Long id, Model model) {
        Course course = courseRepository.findOne(id);
        model.addAttribute("course", course);
        return "course/courseEditForm";
    }

    //@RolesAllowed({"TEACHER", "ASSISTANT"})
    @RequestMapping(value = "/courses/{id}/edit", method = RequestMethod.POST)
    public String editCourse(@PathVariable Long id, @RequestParam String name, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date courseStart, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date courseEnd, @RequestParam Integer numOfWeeks) {
        Course course = courseRepository.findOne(id);
        course.setName(name);
        course.setCourseStart(courseStart);
        course.setCourseEnd(courseEnd);

        courseRepository.save(course);

        return "redirect:/courses/" + course.getId();
    }

}
