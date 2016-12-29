/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.controller;

import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.SubmissionRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.Submission;
import wepa.wepa.domain.Week;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.WeekRepository;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("nosec")
public class SubmissionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    public void createSubmission(Course course, Week week, Person person, Submission submission) {

        String nameP = UUID.randomUUID().toString();
        person.setName(nameP);
        if (person.getStudentNumber() == null) {
            person.setStudentNumber("000011100");
        }
        personRepository.save(person);

        String nameC = UUID.randomUUID().toString();
        course.setName(nameC);
        courseRepository.save(course);

        String description = UUID.randomUUID().toString();
        week.setDescription(description);
        week.setCourse(course);
        weekRepository.save(week);

        submission.setWeek(week);
        submission.setStudent(person);
        submission.setExerciseCount(3);
    }

    @Test
    public void getOk() throws Exception {
        mockMvc.perform(get("/exercises")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getSubmissionOk() throws Exception {
        Course course = new Course();
        Week week = new Week();
        Person person = new Person();
        Submission submission = new Submission();
        createSubmission(course, week, person, submission);
        submissionRepository.save(submission);

        mockMvc.perform(get("/submissions/" + submission.getId())).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void postSubmissionWrong() throws Exception {
        Course course = new Course();
        Week week = new Week();
        Person person = new Person();
        person.setStudentNumber("000000011");
        Submission submission = new Submission();
        createSubmission(course, week, person, submission);
        String name = UUID.randomUUID().toString();
        mockMvc.perform(post("/submissions/courses/" + course.getId() + "/week/ " + week.getWeek()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param("studentNumber", "").param("name", name).param("exerciseCount", "0").param("exerciseSubmission", ""));

        List<Submission> submissions = submissionRepository.findAll();

        boolean found = false;

        for (Submission submission1 : submissions) {
            if (submission1.getStudent().getName().equals(name)) {
                found = true;
            }
        }

        assertFalse(found);
    }

    @Test
    public void postSubmission() throws Exception {
        Course course = new Course();
        Week week = new Week();
        Person person = new Person();
        person.setStudentNumber("000000111");
        Submission submission = new Submission();
        createSubmission(course, week, person, submission);
        mockMvc.perform(post("/submissions/courses/" + course.getId() + "/week/ " + week.getWeek()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param("studentNumber", person.getStudentNumber()).param("name", person.getName()).param("exerciseCount", "5").param("exerciseSubmission", ""));

        List<Submission> submissions = submissionRepository.findAll();

        boolean found = false;

        for (Submission submission1 : submissions) {
            if (submission1.getStudent().getName().equals(person.getName()) && submission1.getStudent().getStudentNumber().equals(person.getStudentNumber())) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void postSubmissionNullPerson() throws Exception {
        Course course = new Course();
        Week week = new Week();
        Person person = new Person();
        person.setStudentNumber("000011111");
        Submission submission = new Submission();
        createSubmission(course, week, person, submission);
        
        Person newPerson = new Person();
        newPerson.setStudentNumber("111111110");
        String nameP = UUID.randomUUID().toString();
        newPerson.setName(nameP);
        
        assertNull(personRepository.findByName(nameP));
        
        mockMvc.perform(post("/submissions/courses/" + course.getId() + "/week/ " + week.getWeek()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param("studentNumber", newPerson.getStudentNumber()).param("name", newPerson.getName()).param("exerciseCount", "5").param("exerciseSubmission", ""));

        assertNotNull(personRepository.findByName(nameP));

        List<Submission> submissions = submissionRepository.findAll();

        boolean found = false;

        for (Submission submission1 : submissions) {
            if (submission1.getStudent().getName().equals(newPerson.getName()) && submission1.getStudent().getStudentNumber().equals(newPerson.getStudentNumber())) {
                found = true;
            }
        }
        assertTrue(found);
    }
}
