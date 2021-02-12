package hu.fitforfun.controller;

import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.repositories.SportFacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/facilities")
public class SportFacilityController {

    @Autowired
    private SportFacilityRepository sportFacilityRepository;


    @GetMapping("")
    public ResponseEntity<Iterable<SportFacility>> getFacilities() {
        return ResponseEntity.ok(sportFacilityRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportFacility> get(@PathVariable Long id) {
        Optional<SportFacility> facility = sportFacilityRepository.findById(id);
        if (facility.isPresent()) {
            return ResponseEntity.ok(facility.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/post")
    public ResponseEntity<SportFacility> post(@RequestBody SportFacility facility) {
        SportFacility savedEntity = sportFacilityRepository.save(facility);
        return ResponseEntity.ok(savedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<SportFacility> facility = sportFacilityRepository.findById(id);
        if (!facility.isPresent()) {
            ResponseEntity.notFound();
        }
        sportFacilityRepository.delete(facility.get());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportFacility> put(@PathVariable Long id, @RequestBody SportFacility facility) {
        Optional<SportFacility> found = sportFacilityRepository.findById(id);
        if (!found.isPresent()) {
            ResponseEntity.notFound();
        }
        SportFacility instToUpdate = found.get();
        if (facility.getName() != null) {
            instToUpdate.setName(facility.getName());
        }
        return ResponseEntity.ok(sportFacilityRepository.save(instToUpdate));
    }

    @GetMapping("/{id}/instructors")
    public ResponseEntity<Iterable<Instructor>> getInstructors(@PathVariable Long id) {
        Optional<SportFacility> facility = sportFacilityRepository.findById(id);
        if (facility.isPresent()) {
            return ResponseEntity.ok(facility.get().getInstructors());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


