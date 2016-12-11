
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
import wepa.wepa.repository.ExerciseRepository;
import wepa.wepa.repository.WeeklyExerciseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExerciseTest {
    
    @Autowired
    WeeklyExerciseRepository weRepo;
    
    @Autowired
    ExerciseRepository exRepo;
    
    @Test
    public void test1(){
        WeeklyExercise we = new WeeklyExercise();
        we.setWeek(1);
        we = weRepo.save(we);
        Exercise ex = new Exercise();
        ex.setDescription("hello test");
        ex.setWeek(we);
        ex = exRepo.save(ex);
        
        Exercise retrieved = exRepo.findOne(ex.getId());
        assertEquals("hello test", retrieved.getDescription());
        assertEquals(1, retrieved.getWeek().getWeek());
    }
}
