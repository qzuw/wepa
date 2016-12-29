package wepa.wepa.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Submission extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn
    private Person student;
    @ManyToOne
    @JoinColumn
    private Week week;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionTime;

    @Min(0)
    private int exerciseCount;

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

    public int getExerciseCount() {
        return exerciseCount;
    }

    public void setExerciseCount(int exerciseCount) {
        this.exerciseCount = exerciseCount;
    }

    public String getExerciseSubmission() {
        return exerciseSubmission;
    }

    public void setExerciseSubmission(String exerciseSubmission) {
        this.exerciseSubmission = exerciseSubmission;
    }

    public Date getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Date submissionTime) {
        this.submissionTime = submissionTime;
    }

}
