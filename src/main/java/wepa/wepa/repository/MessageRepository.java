package wepa.wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.wepa.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    
}
