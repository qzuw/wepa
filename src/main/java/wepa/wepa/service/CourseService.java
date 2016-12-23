/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.PersonRepository;

@Service
public class CourseService {

    @Autowired
    private PersonRepository personRepository;

    public Page<Person> getPersonPage(Integer pageNumber, Long courseId) {
        Pageable request = new PageRequest(pageNumber, 10, Sort.Direction.ASC, "studentNumber");
        return personRepository.findByCoursesAttended(courseId, request);
    }
}
