
package wepa.wepa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.Submission;
import wepa.wepa.domain.Week;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByWeek(Week week);
    List<Submission> findByStudent(Person person);
}
