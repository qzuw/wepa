package wepa.wepa.controller;

import java.util.UUID;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wepa.wepa.domain.Course;
import wepa.wepa.repository.CourseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

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
