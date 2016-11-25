package wepa.wepa.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Message extends AbstractPersistable<Long> {
    
    private String messageContent;
    @ManyToOne
    @JoinColumn
    private Language messageLanguage;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Language getMessageLanguage() {
        return messageLanguage;
    }

    public void setMessageLanguage(Language messageLanguage) {
        this.messageLanguage = messageLanguage;
    }
    
    
}
