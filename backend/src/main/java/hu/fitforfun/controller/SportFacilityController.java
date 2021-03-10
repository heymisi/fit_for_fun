package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.user.User;
import hu.fitforfun.services.SportFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/facilities")
public class SportFacilityController {

    @Autowired
    private SportFacilityService sportFacilityService;


    @GetMapping("")
    public List<SportFacility> getSportFacilities(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
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
    @PostMapping("/{id}/rate")
    public Response rateSportFacility(@PathVariable Long id, @RequestParam(value = "value") Double value) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return Response.createOKResponse(sportFacilityService.rateSportFacility((User)auth.getDetails(),id, value));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }
}


