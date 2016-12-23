package wepa.wepa.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wepa.wepa.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByName(String name);
    
    Person findByStudentNumber(String studentNumber);
    
    @Query("SELECT person FROM Person person INNER JOIN person.coursesAttended courses WHERE courses.id = :coursesId")
    Page<Person> findByCoursesAttended(@Param("coursesId") Long coursesId, Pageable pageable);
    
}
