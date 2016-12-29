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

    private String personname;

    @Before
    public void setUp() {
        Person testperson = new Person();
        personname = UUID.randomUUID().toString();
        testperson.setName(personname);
        testperson.setPassword(passwordEncoder().encode(personname));
        testperson.setStudentNumber("987654321");
        testperson.setAuthorities(Arrays.asList("TEACHER"));
        personRepository.save(testperson);

    }

    @Test
    public void loginTest() {
        goTo("http://localhost:" + port + "/login");

        fill(find("input")).with(personname);
        fill(find("input")).with(personname);
        submit(find("form").first());

        goTo("http://localhost:" + port + "/persons/" + personRepository.findByName(personname).getId());

        assertFalse(pageSource().contains("555555555"));
        assertFalse(pageSource().contains("Maija"));
        assertTrue(pageSource().contains(personname));

    }

    //        
//        goTo("http://localhost:" + port + "/exercises");
//
//        fill(find("#studentNumber")).with("555555555");
//        fill(find("#name")).with("Maija");
//        fill(find("#exercises")).with("4");
//        submit(find("form").first());
//
//        goTo("http://localhost:" + port + "/persons");
//        
//        assertTrue(pageSource().contains("555555555"));
//        assertTrue(pageSource().contains("Maija"));

}
