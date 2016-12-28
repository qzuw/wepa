
package wepa.wepa.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Course extends AbstractPersistable<Long> {

    @NotBlank
    private String name;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date courseStart;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date courseEnd;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    private List<Week> weeks;
    
    @ManyToMany
    private List<Person> assistants;
    
    @ManyToMany
    private List<Person> students;

    private LogHandle logHandle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public int getNumOfWeeks() {
        return weeks.size();
    }

    public int getNumOfStudents() {
        return students.size();
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

    public List<Person> getAssistants() {
        return assistants;
    }

    public void setAssistants(List<Person> assistants) {
        this.assistants = assistants;
    }

    public List<Person> getStudents() {
        return students;
    }

    public void setStudents(List<Person> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course name: " + name + ", courseStart:" + courseStart + ", courseEnd:" + courseEnd + ", weeks: " + weeks.size();
    }

    public LogHandle getLogHandle() {
        return logHandle;
    }

    public void setLogHandle(LogHandle logHandle) {
        this.logHandle = logHandle;
    }

}
