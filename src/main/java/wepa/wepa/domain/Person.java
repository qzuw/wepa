package wepa.wepa.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import wepa.wepa.repository.LogHandleRepository;

@Entity
public class Person extends AbstractPersistable<Long> {

    @NotBlank
    @Length(min = 9, max = 9)
    @Column(unique = true)
    private String studentNumber;

    @NotBlank
    private String name;

    @ManyToOne
    private Language language;

    private String password;

    @ManyToMany(mappedBy = "students")
    private List<Course> coursesAttended;

    @ManyToMany(mappedBy = "assistants")
    private List<Course> coursesAssisted;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    private LogHandle logHandle;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String number) {
        this.studentNumber = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String nickname) {
        this.name = nickname;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public List<Course> getCoursesAttended() {
        return coursesAttended;
    }

    public void setCoursesAttended(List<Course> coursesAttended) {
        this.coursesAttended = coursesAttended;
    }

    public List<Course> getCoursesAssisted() {
        return coursesAssisted;
    }

    public void setCoursesAssisted(List<Course> coursesAssisted) {
        this.coursesAssisted = coursesAssisted;
    }

    public LogHandle getLogHandle() {
        return logHandle;
    }

    public void setLogHandle(LogHandle logHandle) {
        this.logHandle = logHandle;
    }

}
