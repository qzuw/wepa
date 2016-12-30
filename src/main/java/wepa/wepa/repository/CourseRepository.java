
package wepa.wepa.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wepa.wepa.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
    @Query("SELECT c from Course c WHERE "
            + "c.courseStart <= :now AND c.courseEnd >= :now")
    List<Course> findCurrentCourses(@Param("now") Date now);

    @Query("SELECT c from Course c WHERE "
            + "c.courseStart > :now")
    List<Course> findFutureCourses(@Param("now") Date now);
}
