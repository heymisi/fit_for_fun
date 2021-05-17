package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.Image;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.request.InstructorRegistrationModel;
import hu.fitforfun.model.request.InstructorResponseModel;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.InstructorRepository;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.services.InstructorService;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "")
    public Iterable<Instructor> getInstructorsFiltered(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @Join(path = "knownSports", alias = "o") // alias specified for joined path
            @And({@Spec(path = "user.firstName", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "user.shippingAddress.city.cityName", params = "address", spec = EqualIgnoreCase.class),
                    @Spec(path = "o.name", params = "sport", spec = EqualIgnoreCase.class)})
                    Specification<Instructor> spec) {
        return instructorService.listInstructorsFiltered(page, limit, spec);
    }


    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        try {
            return Response.createOKResponse(instructorService.getInstructorById(id));
        } catch (FitforfunException | IOException e) {
            return Response.createErrorResponse("error to get Instructor");
        }
    }


    @GetMapping("/byUser/{userId}")
    public Response getByUser(@PathVariable Long userId) {
        try {
            return Response.createOKResponse(instructorService.getInstructorByUserId(userId));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PostMapping("")
    public Response createInstructor(@RequestBody InstructorRegistrationModel instructor) {
        try {
            return Response.createOKResponse(instructorService.createInstructor(instructor));
        } catch (Exception e) {
            return Response.createErrorResponse("error creating instructor");
        }
    }

    @PutMapping("/{id}")
    public Response updateInstructor(@PathVariable Long id, @RequestBody InstructorResponseModel instructor) {
        try {
            instructorService.updateInstructor(id, instructor);
            return Response.createOKResponse("success instructor update");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }


    @PutMapping("/{id}/updateUser")
    public Response updateInstructorUser(@PathVariable Long id, @RequestBody User user) {
        try {
            instructorService.updateInstructorUser(id, user);
            return Response.createOKResponse("success instructor user update");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id, @RequestParam(value = "pass") String pass) {
        try {
            return instructorService.deleteInstructor(id, pass);
        } catch (FitforfunException e) {
            return false;
        }
    }

    @GetMapping("/availableFacility")
    public List<Instructor> getInstructorsByAvailableFacility() {
        return instructorService.listInstructorsByAvailableFacility();
    }

    @PostMapping("/{id}/addComment")
    public Response addComment(@PathVariable Long id, @RequestBody CommentRequestModel massage) {
        try {
            return Response.createOKResponse(instructorService.addComment(id, massage));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PostMapping("/{id}/uploadImage")
    public Response uploadImage(@PathVariable Long id, @RequestParam("imageFile") MultipartFile file) {
        try {
            instructorService.addImage(id, file);
            return Response.createOKResponse("Success Image upload");
        } catch (Exception e) {
            return Response.createErrorResponse("Error during image upload");
        }
    }

    @GetMapping(path = {"/getImage/{imageName}"})
    public Image getImage(@PathVariable("imageName") String imageName) {
        try {
            return imageService.getImage(imageName);
        } catch (IOException e) {
            return null;
        }
    }

}
