
package wepa.wepa.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
public class StudentExercise extends AbstractPersistable<Long>{
    
    
    private Person student;
    private ExerciseMeeting meeting;
    private int maara;
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

    public int getMaara() {
        return maara;
    }

    public void setMaara(int maara) {
        this.maara = maara;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
    
    
}
