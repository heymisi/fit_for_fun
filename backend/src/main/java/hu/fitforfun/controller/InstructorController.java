package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.repositories.InstructorRepository;
import hu.fitforfun.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorService instructorService;

    @GetMapping("")
    public Page<Instructor> getInstructors(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return instructorService.listInstructors(page,limit);
    }

    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        try {
            return Response.createOKResponse(instructorService.getInstructorById(id));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
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

    @GetMapping("/availableFacility")
    public List<Instructor> getInstructorsByAvailableFacility() {
        return instructorService.listInstructorsByAvailableFacility();
    }
/*
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
    }*/

    /*@GetMapping("/{id}/sportfacility")
    public ResponseEntity<SportFacility> sportFacilityResponseEntity(@PathVariable Long id) {
        Optional<Instructor> inst = instructorRepository.findById(id);
        if (inst.isPresent()) {
            return ResponseEntity.ok();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
