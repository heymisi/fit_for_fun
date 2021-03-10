package hu.fitforfun.repositories;

import hu.fitforfun.model.SportFacility;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SportFacilityRepository extends PagingAndSortingRepository<SportFacility, Long> {

    Optional<SportFacility> findByName(String name);

}