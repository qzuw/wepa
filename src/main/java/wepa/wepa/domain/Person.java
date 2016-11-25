package wepa.wepa.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Person extends AbstractPersistable<Long>{
    
    private String studentNumber;
    private String name;
    private Language language;
    
    public String getStudentNumber(){
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
    
}
