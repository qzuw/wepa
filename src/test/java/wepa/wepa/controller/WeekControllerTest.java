package wepa.wepa.controller;

import java.util.UUID;
import javax.servlet.Filter;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Week;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.WeekRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("nosec")
@ContextConfiguration
@WebAppConfiguration
public class WeekControllerTest {

    private MockMvc mock;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setUp() {
        this.mock = MockMvcBuilders.webAppContextSetup(webAppContext).addFilters(springSecurityFilterChain).build();
    }

    public void createWeek(Course course, Week week) {

        String name = UUID.randomUUID().toString();
        course.setName(name);
        courseRepository.save(course);

        String description = UUID.randomUUID().toString();
        week.setDescription(description);
        week.setCourse(course);
        weekRepository.save(week);
    }

    @Test
    public void getWeekOk() throws Exception {
        Course course = new Course();
        Week week = new Week();
        createWeek(course, week);

        mock.perform(get("/courses/" + course.getId() + "/week/ " + week.getWeek())).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getWeekModifyFormOk() throws Exception {
        Course course = new Course();
        Week week = new Week();
        createWeek(course, week);
        mock.perform(get("/courses/" + course.getId() + "/week/ " + week.getWeek() + "/modifyWeek")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void editWeekWrong() throws Exception {
        Course course = new Course();
        Week week = new Week();
        createWeek(course, week);
        mock.perform(post("/courses/" + course.getId() + "/week/ " + week.getWeek() + "/modifyWeek").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("description", ""));

        Week retrieved = weekRepository.findOne(week.getId());

        assertFalse(retrieved.getDescription().equals(""));
    }

    @Test
    public void editWeek() throws Exception {
        Course course = new Course();
        Week week = new Week();
        createWeek(course, week);
        String description = UUID.randomUUID().toString();
        mock.perform(post("/courses/" + course.getId() + "/week/ " + week.getWeek() + "/modifyWeek").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("description", description));

        Week retrieved = weekRepository.findOne(week.getId());

        assertTrue(retrieved.getDescription().equals(description));
    }
}
