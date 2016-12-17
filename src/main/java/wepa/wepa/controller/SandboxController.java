package wepa.wepa.controller;

import java.util.*;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wepa.wepa.Wepa;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Exercise;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.Week;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.ExerciseRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.WeekRepository;

@Profile("default")
@Controller
public class SandboxController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private WeekRepository weekRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;

    static Logger log = Logger.getLogger(Wepa.class.getName());

    @PostConstruct
    public void init() {

//        Person user = new Person();
//        user.setName("teach");
//        user.setStudentNumber("000000000");
//        user.setPassword(passwordEncoder.encode("teach"));
//        user.setAuthorities(Arrays.asList("TEACHER"));
//        personRepository.save(user);
//        log.info("Inserting test data.");
//        
//        Person user1 = new Person();
//        user1.setName("assistant");
//        user1.setStudentNumber("111111111");
//        user1.setPassword(passwordEncoder.encode("assistant"));
//        user1.setAuthorities(Arrays.asList("ASSISTANT"));
//        personRepository.save(user1);
//        List<Person> assistants = new ArrayList<>();
//        assistants.add(user1);
//
//        Person user2 = new Person();
//        user2.setName("student");
//        user2.setStudentNumber("222222222");
//        user2.setPassword(passwordEncoder.encode("student"));
//        user2.setAuthorities(Arrays.asList("STUDENT"));
//        personRepository.save(user2);
//        List<Person> students = new ArrayList<>();
//        students.add(user2);
//
//        Course c = new Course();
//        c.setName("Test course");
//        c.setAssistants(assistants);
//        c.setStudents(students);
//        c.setCourseStart(new Date(System.currentTimeMillis() - 100000000));
//        c.setCourseEnd(new Date(System.currentTimeMillis() + 100000000));
//        courseRepository.save(c);
//        List<Week> welist = new ArrayList<>();
//        for (int i = 1; i <= 6; i++) {
//            Week we = new Week();
//            we.setCourse(c);
//            we.setDescription("Test week " + i);
//            we.setWeek(i);
//            weekRepository.save(we);
//            List<Exercise> exerciselist = new ArrayList<>();
//            for (int j = 1; j <= 3; j++) {
//                Exercise em = new Exercise();
//                em.setWeek(we);
//                em.setDescription("Exercise " + j + " for week " + i);
//                exerciseRepository.save(em);
//                exerciselist.add(em);
//            }
//            we.setExercises(exerciselist);
//            weekRepository.save(we);
//            welist.add(we);
//        }
//        c.setWeeks(welist);
//        courseRepository.save(c);
//        List<Course> courselist = new ArrayList<>();
//        courselist.add(c);
//
//        user1.setCoursesAssisted(courselist);
//        personRepository.save(user1);
//
//        user2.setCoursesAttended(courselist);
//        personRepository.save(user2);

    }

    @RequestMapping("/sandbox")
    public String handleDefault() {

        return "week/showWeek";
    }
}
