package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.services.SportFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/facilities")
public class SportFacilityController {

    @Autowired
    private SportFacilityService sportFacilityService;


    @GetMapping("")
    public Page<SportFacility> getSportFacilities(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return sportFacilityService.listSportFacilities(page, limit);
    }

    @GetMapping("/{id}")
    public Response getSportFacilityById(@PathVariable Long id) {
        try {
            return Response.createOKResponse(sportFacilityService.getSportFacilityById(id));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
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
    //@PreAuthorize("hasAuthority('DELETE_AUTHORITY') or #id == principal.userId")
    @DeleteMapping("/{id}")
    public Response deleteSportFacility(@PathVariable Long id) {
        try {
            sportFacilityService.deleteSportFacility(id);
            return Response.createOKResponse("Successful delete");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping("/search/{keyword}")
    public Page<SportFacility> searchFacilityByNameContaining
            (@PathVariable String keyword,
             @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "limit", defaultValue = "5") int limit) {
        System.err.println(keyword);
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

    @PostMapping("/{id}/uploadImage")
    public Response uploadImage(@PathVariable Long id,@RequestParam("imageFile") MultipartFile file)   {
        try{
            sportFacilityService.addImage(id,file);
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

   /* @PostMapping("/{id}/rate")
    public Response rateSportFacility(@PathVariable Long id, @RequestParam(value = "value") Double value) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return Response.createOKResponse(sportFacilityService.rateSportFacility((User)auth.getDetails(),id, value));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }*/
}


