package wepa.wepa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Exercise extends AbstractPersistable<Long> {
    
    @Id
    private Long id;
    
    @NotBlank
    private String description;
    
    @ManyToOne
    @JoinColumn
    private Week week;

    private LogHandle logHandle;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public LogHandle getLogHandle() {
        return logHandle;
    }

    public void setLogHandle(LogHandle logHandle) {
        this.logHandle = logHandle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
}
