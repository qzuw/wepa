package wepa.wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.wepa.domain.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    
}
