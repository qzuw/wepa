package wepa.wepa.selenium;

import java.util.Arrays;
import java.util.UUID;
import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class wepaTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private PersonRepository personRepository;

    private String studentnumber;

    @Before
    public void setUp() {
        Person oldPerson = personRepository.findByStudentNumber("987654321");
        if (oldPerson != null) {
            personRepository.delete(oldPerson);
        }
        String personname = UUID.randomUUID().toString();
        studentnumber = "987654321";
        Person testperson = new Person();
        testperson.setName(personname);
        testperson.setPassword(passwordEncoder().encode(studentnumber));
        testperson.setStudentNumber(studentnumber);
        testperson.setAuthorities(Arrays.asList("TEACHER"));
        personRepository.save(testperson);

    }

    private void login(String s) {
        goTo("http://localhost:" + port + "/login");

        fill(find("input")).with(s);
        fill(find("input")).with(s);
        submit(find("form").first());

    }

    @Test
    public void loginTest() {

        login(studentnumber);

        goTo("http://localhost:" + port + "/persons/" + personRepository.findByStudentNumber(studentnumber).getId());

        assertFalse(pageSource().contains("555555555"));
        assertFalse(pageSource().contains("Maija"));
        assertTrue(pageSource().contains(studentnumber));

    }

    @Test
    public void addCourseTest() {
        login(studentnumber);

        assertFalse(pageSource().contains("login"));
        assertTrue(pageSource().contains("logout"));

        String courseName = "c" + UUID.randomUUID().toString();

        goTo("http://localhost:" + port + "/courses");

        assertFalse(pageSource().contains(courseName));

        goTo("http://localhost:" + port + "/courses/new");

        fill(find("#name")).with(courseName);
        fill(find("#courseStart")).with("1999-12-12");
        fill(find("#courseEnd")).with("2020-09-09");
        fill(find("#weeks")).with("4");

        submit(find("#addcourse").first());

        goTo("http://localhost:" + port + "/courses");

        assertTrue(pageSource().contains(courseName));

        click(find("#" + courseName).first());

        assertTrue(pageSource().contains(courseName));
        assertTrue(pageSource().contains("1999-12-12"));
        assertTrue(pageSource().contains("2020-09-09"));

    }

}
