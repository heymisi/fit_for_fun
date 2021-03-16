package hu.fitforfun.repositories;

import hu.fitforfun.model.Exercise;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends PagingAndSortingRepository<Exercise, Long> {

    Optional<Exercise> findByName(String name);
}
