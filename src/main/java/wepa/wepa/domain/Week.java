package wepa.wepa.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Week extends AbstractPersistable<Long> {

    private int week;
    private String description;
    @ManyToOne
    @JoinColumn
    private Course course;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "week")
    private List<Exercise> exercises;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "week")
    private List<Submission> submissions;

    private LogHandle logHandle;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getNumOfStudents() {
        return 0;
    }

    public int getNumOfExercises() {
        return exercises.size();
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public LogHandle getLogHandle() {
        return logHandle;
    }

    public void setLogHandle(LogHandle logHandle) {
        this.logHandle = logHandle;
    }
    

}
