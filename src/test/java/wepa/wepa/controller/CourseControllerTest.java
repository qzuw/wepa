package wepa.wepa.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wepa.wepa.domain.Course;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void getOk() throws Exception {
        mockMvc.perform(get("/courses")).andExpect(status().is2xxSuccessful());
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

        mockMvc.perform(get("/courses/"+ course.getId() +"/edit")).andExpect(status().is2xxSuccessful());
    }
    
    //@Test
    public void editCourseOk()throws Exception{
        Course course = new Course();
        course.setName("Testikurssii");
        course = courseRepository.save(course);
        
        mockMvc.perform(post("/courses/"+ course.getId()+"/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "UusiTesti"));
//                .param("courseStart", "2017-07-07")
//                .param("courseEnd", "2017-08-08"));
        
        
        Course course2 = courseRepository.findOne(course.getId());
        
        assertNotNull(course2);
        assertEquals("UusiTesti", course2.getName());
        
    }

    @Test
    public void getContainsEditedCourse() throws Exception {
         Course course = new Course();
        course.setName("Testikurssi");
        courseRepository.save(course);

        mockMvc.perform(get("/courses/"+ course.getId() +"/edit")).andExpect(model().attributeExists("course"));
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

        res = mockMvc.perform(get("/courses/" + c.getId())).andReturn();
        content = res.getResponse().getContentAsString();
        assertTrue(content.contains(name));

    }
}
