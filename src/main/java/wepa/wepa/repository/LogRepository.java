package wepa.wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.wepa.domain.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
