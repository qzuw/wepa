package wepa.wepa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.WeeklyExercise;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.WeeklyExerciseRepository;

@Controller

public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WeeklyExerciseRepository weeklyExerciseRepository;

    @RequestMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "listCourses";
    }

    @RequestMapping("/courses/{id}")
    public String showCourse(Model model, @PathVariable Long id) {
        Course course = courseRepository.findOne(id);
        model.addAttribute("course", course);
        return "showCourse";
    }

    @RequestMapping("/courses/new")
    public String courseAddForm() {
        return "courseAddForm";
    }

    @RequestMapping(value = "/courses/new", method = RequestMethod.POST)
    public String addCourse(@RequestParam String name, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date courseStart, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date courseEnd, @RequestParam Integer weeks) {
        Course course = new Course();
        course.setName(name);
        course.setCourseStart(courseStart);
        course.setCourseEnd(courseEnd);
        courseRepository.save(course);
        List<WeeklyExercise> weeklist = new ArrayList<>();
        for (int i = 1; i <= weeks; i++) {
            WeeklyExercise we = new WeeklyExercise();
            we.setWeek(i);
            we.setCourse(course);
            weeklyExerciseRepository.save(we);
            weeklist.add(we);
        }
        course.setWeeks(weeklist);

        courseRepository.save(course);

        return "redirect:/courses/" + course.getId();
    }

    @RequestMapping("/courses/{id}/edit")
    public String courseEditForm(@PathVariable Long id, Model model) {
        Course course = courseRepository.findOne(id);
        model.addAttribute("course", course);
        return "courseEditForm";
    }

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
