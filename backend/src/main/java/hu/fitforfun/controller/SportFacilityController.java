package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.Image;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.services.SportFacilityService;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/facilities")
public class SportFacilityController {

    @Autowired
    private SportFacilityService sportFacilityService;
    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public Response getSportFacilityById(@PathVariable Long id) {
        try {
            return Response.createOKResponse(sportFacilityService.getSportFacilityById(id));
        } catch (FitforfunException | IOException e) {
            return Response.createErrorResponse("error to get Facility");
        }
    }

    @PostMapping({"", "/"})
    public Response saveSportFacility(@RequestBody SportFacility sportFacility) {
        try {
            return Response.createOKResponse(sportFacilityService.createSportFacility(sportFacility));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PutMapping("/{id}")
    public Response updateSportFacility(@PathVariable Long id, @RequestBody SportFacility sportFacility) {
        try {
            return Response.createOKResponse(sportFacilityService.updateSportFacility(id, sportFacility));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @DeleteMapping("/{id}")
    public Response deleteSportFacility(@PathVariable Long id) {
        try {
            sportFacilityService.deleteSportFacility(id);
            return Response.createOKResponse("Successful delete");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping(value = "")
    public Iterable<SportFacility> getFacilitiesFiltered(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @Join(path= "availableSports", alias = "o") // alias specified for joined path
            @And({@Spec(path = "name", params="name",spec = LikeIgnoreCase.class),
                    @Spec(path = "address.city.cityName", params = "address",spec = EqualIgnoreCase.class),
                    @Spec(path="o.name", params="sport", spec=EqualIgnoreCase.class)})
                    Specification<SportFacility> spec) {
        return sportFacilityService.listSportFacilities(page, limit, spec);
    }

    @GetMapping("/search/{keyword}")
    public Page<SportFacility> searchFacilityByNameContaining
            (@PathVariable String keyword,
             @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return sportFacilityService.findByNameContaining(keyword, PageRequest.of(page, limit));
    }

    @GetMapping("/search/city/{city}")
    public Page<SportFacility> searchFacilityByCity
            (@PathVariable String city,
             @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return sportFacilityService.findByCity(city, PageRequest.of(page, limit));
    }

    @GetMapping("/sport/{id}")
    public Page<SportFacility> getFacilityBySportId(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return sportFacilityService.listFacilitiesBySportId(id, page, limit);
    }

    @PostMapping("/{id}/uploadImage/{type}")
    public Response uploadImage(@PathVariable Long id, @PathVariable String type, @RequestParam("imageFile") MultipartFile file)   {
        try{
            sportFacilityService.addImage(id,file,type);
            return Response.createOKResponse("Success Image upload");
        }catch (Exception e){
            return Response.createErrorResponse("Error during image upload");
        }
    }
    @PostMapping("/{id}/addInstructor")
    public Response addInstructor(@PathVariable Long id, @RequestParam("Instructor")Instructor instructor)   {
        try{
            sportFacilityService.addInstructor(id,instructor);
            return Response.createOKResponse("Success Instructor added");
        }catch (Exception e){
            return Response.createErrorResponse("Error during Instructor added");
        }
    }

    @PostMapping("/{id}/addComment")
    public Response addComment(@PathVariable Long id, @RequestBody CommentRequestModel massage) {
        try{
            return Response.createOKResponse(sportFacilityService.addComment(id, massage));
        }catch (FitforfunException e){
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping(path = { "/getImage/{imageName}" })
    public Image getImage(@PathVariable("imageName") String imageName) {
        try{
            return imageService.getImage(imageName);
        }catch (IOException e){
            return null;
        }
    }

}


