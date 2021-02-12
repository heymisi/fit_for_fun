package hu.fitforfun.repositories;

import hu.fitforfun.model.SportFacility;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SportFacilityRepository extends PagingAndSortingRepository<SportFacility, Long> {

}