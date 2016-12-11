package wepa.wepa.domain;

import java.util.Arrays;
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
import wepa.wepa.repository.LanguageRepository;
import wepa.wepa.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest {

    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    LanguageRepository languageRepository;

    
    @Test
    public void testPerson() {
        Person person = new Person();
        person.setName("Nakki");
        person.setStudentNumber("000000000");

        assertEquals("Nakki", person.getName());
        assertEquals("000000000", person.getStudentNumber());

    }

    @Test
    public void testSavePerson() {
        Person person = new Person();
        person.setName("Nakki");
        person.setStudentNumber("000000000");

        personRepository.save(person);

        Person retrieved = personRepository.findOne(person.getId());
        assertNotNull(retrieved);
        assertEquals("Nakki", retrieved.getName());
        assertEquals("000000000", retrieved.getStudentNumber());

    }
    
    @Test
    public void insertPasswordandAuthority(){
        Person user = new Person();
        user.setName("Vellu");
        user.setStudentNumber("777777777");
        user.setPassword("vellu");
        user.setAuthorities(Arrays.asList("TEACHER"));
        
        personRepository.save(user);
        Person retrieved = personRepository.findOne(user.getId());
        assertNotNull(retrieved);
        assertEquals("Vellu", retrieved.getName());
        assertEquals("777777777", retrieved.getStudentNumber());
        assertEquals("vellu", retrieved.getPassword());
        assertEquals("TEACHER", retrieved.getAuthorities().get(0));
    }
    
    @Test
    public void insertLanguage(){
        Language l = new Language();
        l.setLanguageName("Klingon");
        l = languageRepository.save(l);
        
        Person person1 = new Person();
        person1.setName("Patti");
        person1.setStudentNumber("999999999");
        person1.setLanguage(l);

        Person p = personRepository.save(person1);
        
        Person retrieved = personRepository.findOne(person1.getId());
        String lang = retrieved.getLanguage().getLanguageName();
        assertEquals("Klingon", lang);
    }
}
