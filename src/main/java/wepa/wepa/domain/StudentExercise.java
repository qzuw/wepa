
package wepa.wepa.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
public class StudentExercise extends AbstractPersistable<Long>{
    
    @ManyToOne
    private Person student;
    
    private ExerciseMeeting meeting;
    
    private int count;
    private boolean present;

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public ExerciseMeeting getMeeting() {
        return meeting;
    }

    public void setMeeting(ExerciseMeeting meeting) {
        this.meeting = meeting;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int maara) {
        this.count = maara;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
    
    
}
