package hu.fitforfun.repositories;

import hu.fitforfun.model.OpeningHours;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHoursRepository extends PagingAndSortingRepository<OpeningHours, Long> {
}
