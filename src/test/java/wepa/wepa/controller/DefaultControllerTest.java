/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import java.util.Date;
import java.util.UUID;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
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
public class DefaultControllerTest {

    private MockMvc mock;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void setUp() {
        this.mock = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void getOk() throws Exception {
        mock.perform(get("/index")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void modelHasCurrentCourses() throws Exception {
        mock.perform(get("/")).andExpect(model().attributeExists("currentCourses"));
    }

    @Test
    public void frontPageShowsCourse() throws Exception {
        Course c = new Course();
        c.setCourseStart(new Date(System.currentTimeMillis() - 100000));
        c.setCourseEnd(new Date(System.currentTimeMillis() + 100000));
        String name = UUID.randomUUID().toString();
        c.setName(name);
        courseRepository.save(c);

        MvcResult res = mock.perform(get("/")).andReturn();
        String content = res.getResponse().getContentAsString();
        assertTrue(content.contains(name));
    }
}
