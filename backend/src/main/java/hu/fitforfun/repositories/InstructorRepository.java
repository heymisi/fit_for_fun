package hu.fitforfun.repositories;

import hu.fitforfun.model.Instructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InstructorRepository extends PagingAndSortingRepository<Instructor, Long> {

}