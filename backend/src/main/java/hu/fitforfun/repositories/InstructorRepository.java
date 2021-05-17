package hu.fitforfun.repositories;

import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.user.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends PagingAndSortingRepository<Instructor, Long>, JpaSpecificationExecutor<Instructor> {
    Optional<Instructor> findByUser(User user);
}