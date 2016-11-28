
package wepa.wepa.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class ExerciseMeeting extends AbstractPersistable<Long> {
    
    private String description;
    @ManyToOne
    @JoinColumn
    private WeeklyExercise week;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WeeklyExercise getWeek() {
        return week;
    }

    public void setWeek(WeeklyExercise week) {
        this.week = week;
    }
    
}
