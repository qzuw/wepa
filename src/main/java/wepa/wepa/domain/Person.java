package wepa.wepa.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Person extends AbstractPersistable<Long>{
    
    private int studentNumber;
    private String name;
    
    public int getStudentNumber(){
        return studentNumber;
    }
    
    public void setStudentNumber(int number) {
        this.studentNumber = number;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String nickname) {
        this.name = nickname;
    }
    
}
