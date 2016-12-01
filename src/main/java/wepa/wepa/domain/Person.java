package wepa.wepa.domain;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Person extends AbstractPersistable<Long>{
    
    @NotBlank
    @Length(min = 9, max = 9)
    private String studentNumber;
    @NotBlank
    private String name;
    private Language language;
    private String password;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;
    
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

}
