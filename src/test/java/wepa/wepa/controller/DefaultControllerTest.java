/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wepa.wepa.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultControllerTest {
     private MockMvc mock;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private WebApplicationContext webAppContext;
    
    @Before
    public void setUp(){
        this.mock = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
    
    @Test
    public void getOk() throws Exception {
        mock.perform(get("/index")).andExpect(status().is2xxSuccessful());
    }
}
