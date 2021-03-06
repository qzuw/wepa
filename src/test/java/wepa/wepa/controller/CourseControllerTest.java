package wepa.wepa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.Filter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("nosec")
@ContextConfiguration
@WebAppConfiguration
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void getOk() throws Exception {
        mockMvc.perform(get("/courses")).andExpect(status().is2xxSuccessful());
    }

//    @Test
    public void loginTest() throws Exception{
        mockMvc.perform(formLogin("/login").user("admin").password("pass"));
    }
    
    @Test
    public void getContainsCourses() throws Exception {
        mockMvc.perform(get("/courses")).andExpect(model().attributeExists("courses"));
    }

    @Test
    public void getAddFormOk() throws Exception {
        mockMvc.perform(get("/courses/new")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getEditFormOk() throws Exception {
        Course course = new Course();
        course.setName("Testikurssi");
        courseRepository.save(course);

        mockMvc.perform(get("/courses/" + course.getId() + "/edit")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getRedirectFromWrong() throws Exception {
        Long a = courseRepository.count();

        a++;

        mockMvc.perform(get("/courses/" + a)).andExpect(status().is3xxRedirection());
    }

    @Test
    public void editCourseOk() throws Exception {
        Course course = new Course();
        course.setName("Testikurssii");
        course.setCourseStart(new Date(System.currentTimeMillis()));
        course.setCourseEnd(new Date(System.currentTimeMillis() + 1000000));
        course = courseRepository.save(course);

        mockMvc.perform(post("/courses/" + course.getId() + "/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "UusiTesti")
                .param("courseStart", "2017-07-07")
                .param("courseEnd", "2017-08-08")
                .param("numOfWeeks", "0"));

        Course course2 = courseRepository.findOne(course.getId());

        assertNotNull(course2);
        assertEquals("UusiTesti", course2.getName());

    }

    @Test
    public void addCourseOk() throws Exception {

        String name = UUID.randomUUID().toString();

        mockMvc.perform(post("/courses/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("courseStart", "2017-07-07")
                .param("courseEnd", "2017-08-08")
                .param("weeks", "3"));

        List<Course> courses = courseRepository.findAll();
        Course course = null;
        for (Course course1 : courses) {
            if (course1.getName().equals(name)) {
                course = course1;
            }
        }

        assertNotNull(course);
        assertEquals(name, course.getName());
        assertEquals(3, course.getNumOfWeeks());
        assertEquals(3, course.getWeeks().size());

    }

    @Test
    public void getContainsEditedCourse() throws Exception {
        // naming of this method ???
        Course course = new Course();
        course.setName("Testikurssi");
        courseRepository.save(course);

        mockMvc.perform(get("/courses/" + course.getId() + "/edit")).andExpect(model().attributeExists("course"));
    }

    @Test
    public void addedCourseIsListed() throws Exception {
        Course c = new Course();
        String name = UUID.randomUUID().toString();
        c.setName(name);
        c = courseRepository.save(c);

        MvcResult res = mockMvc.perform(get("/courses")).andReturn();
        String content = res.getResponse().getContentAsString();
        assertTrue(content.contains(name));

        res = mockMvc.perform(get("/courses/" + c.getId() + "/page/1")).andReturn();
        content = res.getResponse().getContentAsString();
        assertTrue(content.contains(name));

    }

//    @Test
    public void addExistingStudentToCourse() throws Exception {
        Course c = new Course();
        String name = UUID.randomUUID().toString();
        c.setName(name);
        c.setStudents(new ArrayList<Person>());
        c = courseRepository.save(c);

        Person p = new Person();
        String pname = UUID.randomUUID().toString();
        p.setName(pname);
        p.setStudentNumber("432156789");
        personRepository.save(p);

        assertFalse(c.getStudents().contains(p));

        mockMvc.perform(post("/courses" + c.getId() + "/addStudent").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("studentNumber", p.getStudentNumber()).param("name", pname));

        Person p2 = personRepository.findByStudentNumber(p.getStudentNumber());
        assertTrue(p2.getCoursesAttended().contains(c));

    }

//    @Test
    public void addStudentToCourse() throws Exception {
        Course c = new Course();
        String name = UUID.randomUUID().toString();
        c.setName(name);
        c.setStudents(new ArrayList<Person>());
        c = courseRepository.save(c);

        Person p = new Person();
        String pname = UUID.randomUUID().toString();
        p.setName(pname);
        p.setStudentNumber("432226789");

        assertFalse(c.getStudents().contains(p));

        mockMvc.perform(post("/courses" + c.getId() + "/addStudent").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("studentNumber", p.getStudentNumber()).param("name", pname));

        Person p2 = personRepository.findByStudentNumber(p.getStudentNumber());
        System.out.println("p " + p2);
        assertTrue(p2.getCoursesAttended().contains(c));

    }
}
