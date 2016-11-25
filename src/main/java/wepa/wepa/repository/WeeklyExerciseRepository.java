package wepa.wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.wepa.domain.WeeklyExercise;

public interface WeeklyExerciseRepository extends JpaRepository<WeeklyExercise, Long>{
    
}
