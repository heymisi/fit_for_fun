package hu.fitforfun.controller;

import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.User;
import hu.fitforfun.repository.InstructorRepository;
import hu.fitforfun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;


    @GetMapping("")
    public ResponseEntity<Iterable<Instructor>> getInstructors() {
        return ResponseEntity.ok(instructorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> get(@PathVariable Long id) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        if (instructor.isPresent()) {
            return ResponseEntity.ok(instructor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Instructor> post(@RequestBody Instructor instructor) {
        Instructor savedEntity = instructorRepository.save(instructor);
        return ResponseEntity.ok(savedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Instructor> inst = instructorRepository.findById(id);
        if (!inst.isPresent()) {
            ResponseEntity.notFound();
        }
        instructorRepository.delete(inst.get());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> put(@PathVariable Long id, @RequestBody Instructor instructor) {
        Optional<Instructor> found = instructorRepository.findById(id);
        if (!found.isPresent()) {
            ResponseEntity.notFound();
        }
        Instructor instToUpdate = found.get();
        if(instructor.getFirstName() != null) {
            instToUpdate.setFirstName(instructor.getFirstName());
        }
        return ResponseEntity.ok(instructorRepository.save(instToUpdate));
    }

    @GetMapping("/{id}/sportfacility")
    public ResponseEntity<SportFacility> sportFacilityResponseEntity(@PathVariable Long id) {
        Optional<Instructor> inst = instructorRepository.findById(id);
        if (inst.isPresent()) {
            return ResponseEntity.ok(inst.get().getSportFacility());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
