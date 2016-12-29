/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Exercise;
import wepa.wepa.domain.Week;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.ExerciseRepository;
import wepa.wepa.repository.WeekRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("nosec")
public class ExerciseControllerTest {

    private MockMvc mock;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WebApplicationContext webAppContext;

    public void createExercise(Course course, Week week, Exercise exercise) {

        String name = UUID.randomUUID().toString();
        course.setName(name);
        courseRepository.save(course);
        
        String descriptionW = UUID.randomUUID().toString();
        week.setDescription(descriptionW);
        week.setCourse(course);
        weekRepository.save(week);

        String descriptionE = UUID.randomUUID().toString();
        exercise.setDescription(descriptionE);
        exercise.setWeek(week);
        exerciseRepository.save(exercise);
    }
    
    @Before
    public void setUp() {
        this.mock = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void getEditOk() throws Exception {
        mock.perform(get("/exercises/1/edit")).andExpect(status().is2xxSuccessful());
    }

    @Test
     public void editExerciseWrong() throws Exception {
       Course course = new Course();
        Week week = new Week();
        Exercise exercise = new Exercise();
        createExercise(course, week, exercise);
        mock.perform(post("/exercises/" + exercise.getId() + "/edit").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("description", ""));

        Exercise retrieved = exerciseRepository.findOne(exercise.getId());
        
        assertFalse(retrieved.getDescription().equals(""));
    }
    
    @Test
    public void editExercise() throws Exception {

        Course course = new Course();
        Week week = new Week();
        Exercise exercise = new Exercise();
        createExercise(course, week, exercise);
        
        String description = UUID.randomUUID().toString();

        mock.perform(post("/exercises/" + exercise.getId() + "/edit").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("description", description));
        
        Exercise retrieved = exerciseRepository.findOne(exercise.getId());
        
        assertTrue(retrieved.getDescription().equals(description));
    }
}
