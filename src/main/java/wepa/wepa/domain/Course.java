/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Course extends AbstractPersistable<Long> {
    
    private String name;
    @Temporal(TemporalType.DATE)
    private Date courseStart;
    @Temporal(TemporalType.DATE)
    private Date courseEnd;
    @OneToMany
    private List<WeeklyExercise> weeks;
    @ManyToMany
    private List<Person> assistants;
    @ManyToMany
    private List<Person> students;
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
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

    public int getNumOfWeeks(){
        return weeks.size();
    }

    public int getNumOfStudents(){
        return students.size();
    }
    
    public List<WeeklyExercise> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<WeeklyExercise> weeks) {
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
    
    
}
