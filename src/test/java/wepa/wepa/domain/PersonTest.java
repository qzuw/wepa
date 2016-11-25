package wepa.wepa.domain;

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
import wepa.wepa.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest {

    @Autowired
    PersonRepository pRepo;

    @Test
    public void testPerson() {
        Person person = new Person();
        person.setName("Nakki");
        person.setStudentNumber("00000000");

        assertEquals("Nakki", person.getName());
        assertEquals("00000000", person.getStudentNumber());

    }

    @Test
    public void testSavePerson() {
        Person person = new Person();
        person.setName("Nakki");
        person.setStudentNumber("00000000");

        pRepo.save(person);

        Person retrieved = pRepo.findOne(person.getId());
        assertNotNull(retrieved);
        assertEquals("Nakki", retrieved.getName());
        assertEquals("00000000", retrieved.getStudentNumber());

    }
}
