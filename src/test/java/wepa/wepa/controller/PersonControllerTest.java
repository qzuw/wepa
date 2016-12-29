/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import java.util.List;
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
import wepa.wepa.domain.Person;
import wepa.wepa.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("nosec")
public class PersonControllerTest {
    
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
        mock.perform(get("/persons/page/1")).andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void getRedirect() throws Exception {
        mock.perform(get("/persons")).andExpect(status().is3xxRedirection());
    }
    
    @Test
    public void getAddFormOk() throws Exception {
        mock.perform(get("/persons/add")).andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void getContainsPersons() throws Exception {
        mock.perform(get("/persons/page/1")).andExpect(model().attributeExists("persons"));
    }
    
    @Test
    public void getFindsPerson() throws Exception {  
        Person person = new Person();
        person.setStudentNumber("020000000");
        person.setName("Matti");
        personRepository.save(person);
        mock.perform(get("/persons/1")).andExpect(model().attributeExists("person"));
    }
    
    @Test
    public void postPerson() throws Exception {
        mock.perform(post("/persons/add").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("studentNumber", "888888888").param("name", "Maija"));
        
        List<Person> persons = personRepository.findAll();
        
        boolean found = false;
        
        for(Person person : persons){
            if(person.getStudentNumber().equals("888888888") && person.getName().equals("Maija")){
                found = true;
            }
        }
        assertTrue(found);
    }
}
