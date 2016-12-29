package wepa.wepa.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.wepa.repository.CourseRepository;
import wepa.wepa.repository.PersonRepository;
import wepa.wepa.repository.SubmissionRepository;
import wepa.wepa.repository.WeekRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubmissionTest {

    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    SubmissionRepository submissionRepository;
    
    @Autowired
    WeekRepository weekRepository;

    @Test
    public void testSubmission() {
        Person person = new Person();
        person.setName("Kalja");
        person.setStudentNumber("001000000");
        
        
        Week week = new Week();
        week.setDescription("Week Test");
        
        Submission submission = new Submission();
        submission.setStudent(person);
        submission.setWeek(week);
        submission.setExerciseCount(6);
        submission.setExerciseSubmission("ES Test");

        assertEquals("Kalja", submission.getStudent().getName());
        assertEquals("001000000", submission.getStudent().getStudentNumber());
        assertEquals("Week Test", submission.getWeek().getDescription());
        assertEquals(person, submission.getStudent());
        assertEquals(week, submission.getWeek());
        assertEquals(6, submission.getExerciseCount());
        assertEquals("ES Test", submission.getExerciseSubmission());

    }

    @Test
    public void testSaveSubmission() {
        Person person = new Person();
        person.setName("Kalja");
        person.setStudentNumber("001000011");
        
        personRepository.save(person);
        
        Week week = new Week();
        week.setDescription("Week Test");
        weekRepository.save(week);
        
        Submission submission = new Submission();
        submission.setStudent(person);
        submission.setWeek(week);
        submission.setExerciseCount(6);
        submission.setExerciseSubmission("ES Test");

        submissionRepository.save(submission);

        Submission retrieved = submissionRepository.findOne(submission.getId());
        assertNotNull(retrieved);
        assertEquals(person, retrieved.getStudent());
        assertEquals(week, retrieved.getWeek());
        assertEquals(6, submission.getExerciseCount());
        assertEquals("ES Test", submission.getExerciseSubmission());

    }

    
}
