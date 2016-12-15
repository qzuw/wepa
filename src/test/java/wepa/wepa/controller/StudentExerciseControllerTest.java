/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.StudentExerciseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentExerciseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private StudentExerciseRepository studentExerciseRepository;

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
        mockMvc.perform(get("/exercises")).andExpect(status().is2xxSuccessful());
    }

   // @Test
    public void rightlyFilledForm() throws Exception {

        MvcResult res = mockMvc.perform(post("/fillExercises")
                .param("studentNumber", "555555555")
                .param("name", "Nakki")
                .param("exerciseCount", "4"))
                .andExpect(status().isOk())
                .andReturn();
        String content = res.getResponse().getContentAsString();
        assertTrue(content.contains("555555555"));
    }

    //@Test
    public void wronglyFilledForm() throws Exception {
//
//        MvcResult res = mockMvc.perform(post("/fillExercises").content("{\"studentNumber\":\"" + "55555555" + "\",\"name\":\"" + "Nakki" + "\", \"exerciseCount\":\"" + "4" + "\" }"))
//                .andReturn();
//        String content = res.getResponse().getContentAsString();
//        assertTrue(content.contains("length must be between 9 and 9"));
    MvcResult res = mockMvc.perform(post("/fillExercises")
            .param("studentNumber", "55555555")
            .param("name", "Nakki")
            .param("exerciseCount", "4"))
            .andReturn();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA" + res.getResponse().getContentAsString());
//            .andExpect(view().name("index"))
//            .andExpect(model().attributeHasFieldErrors("studentNumber"));

    }
}
