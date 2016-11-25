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
import wepa.wepa.repository.LogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogTest {

    @Autowired
    LogRepository logRepository;

    public LogTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void anonLog() {
        Log l = new Log();
        l.setLogMessage("hello");

        assertEquals("hello", l.getLogMessage());
    }

    @Test
    public void testLog() {
        Log l = new Log();
        Person p = new Person();
        l.setLogMessage("hello");
        l.setPerson(p);

        assertEquals(p, l.getPerson());
    }

    @Test
    public void testLogWithRepo() {
        Log l = new Log();
        l.setLogMessage("hello");
        logRepository.save(l);

        Log retrieved = logRepository.findOne(l.getId());

        assertEquals(retrieved, l);
    }

}
