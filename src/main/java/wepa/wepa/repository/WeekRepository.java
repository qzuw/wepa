package wepa.wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Week;

public interface WeekRepository extends JpaRepository<Week, Long>{
    
    Week findByCourseAndWeek(Course course, Integer week);
}
