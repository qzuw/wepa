package wepa.wepa.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class LogHandle extends AbstractPersistable<Long> {
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loghandle")
    private List<Log> logs;

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
    
    
}
