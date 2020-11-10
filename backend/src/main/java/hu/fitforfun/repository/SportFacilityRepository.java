package hu.fitforfun.repository;

import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportFacilityRepository extends CrudRepository<SportFacility, Long> {

    List<SportFacility> findAll();

}