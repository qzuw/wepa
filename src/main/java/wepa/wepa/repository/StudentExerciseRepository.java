
package wepa.wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.wepa.domain.Person;

public interface StudentExerciseRepository extends JpaRepository<Person, Long> {
    
}
