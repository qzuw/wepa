
package wepa.wepa.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
public class Submission extends AbstractPersistable<Long>{
    
    @ManyToOne
    @JoinColumn
    private Person student;
    @ManyToOne
    @JoinColumn
    private Week week;
    
    @Min(0)
    private int exerciseCount;
    
    private boolean present;

    private LogHandle logHandle;
    
    private String exerciseSubmission;
    
    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public int getExerciseCount() {
        return exerciseCount;
    }

    public void setExerciseCount(int exerciseCount) {
        this.exerciseCount = exerciseCount;
    }

    public LogHandle getLogHandle() {
        return logHandle;
    }

    public void setLogHandle(LogHandle logHandle) {
        this.logHandle = logHandle;
    }
    
    public String getExerciseSubmission() {
        return exerciseSubmission;
    }

    public void setExerciseSubmission(String exerciseSubmission) {
        this.exerciseSubmission = exerciseSubmission;
    }
  
}
