package wepa.wepa.domain;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.WeeklyExerciseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseTest {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    WeeklyExerciseRepository weeklyExerciseRepository;

    @Test
    public void newCourse() {
        Course c = new Course();
        String name = "asdf course";
        c.setName(name);
        assertEquals(name, c.getName());

        courseRepository.save(c);

        Course retrieved = courseRepository.findOne(c.getId());
        assertEquals(c, retrieved);
    }

    @Test
    public void courseWeeks() {
        Course c = new Course();
        courseRepository.save(c);
        List<WeeklyExercise> weeks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            WeeklyExercise we = new WeeklyExercise();
            we.setWeek(i);
            we.setCourse(c);
            weeklyExerciseRepository.save(we);
            weeks.add(we);
        }
        c.setWeeks(weeks);
        assertEquals(10, c.getNumOfWeeks());

    }

    @Test
    public void courseWeeksWithRepo() {
        Course c = new Course();
        courseRepository.save(c);
        List<WeeklyExercise> weeks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            WeeklyExercise we = new WeeklyExercise();
            we.setWeek(i);
            we.setCourse(c);
            weeklyExerciseRepository.save(we);
            weeks.add(we);
        }
        c.setWeeks(weeks);
        assertEquals(10, c.getNumOfWeeks());

        courseRepository.save(c);

        Course retrieved = courseRepository.findOne(c.getId());
//        assertEquals(c.getNumOfWeeks(), retrieved.getNumOfWeeks());
        assertEquals(c, retrieved);
    }

}
