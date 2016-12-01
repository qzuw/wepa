
package wepa.wepa.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class WeeklyExercise extends AbstractPersistable<Long> {

    private int week;
    private String description;
    @ManyToOne
    @JoinColumn
    private Course course;
    @OneToMany
    private List<ExerciseMeeting> meetings;

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

    public List<ExerciseMeeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<ExerciseMeeting> meetings) {
        this.meetings = meetings;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
    
}
