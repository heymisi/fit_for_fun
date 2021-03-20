package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.ShopItemRepository;
import hu.fitforfun.services.ShopItemService;
import hu.fitforfun.services.SportFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop-items")
public class ShopItemController {

    @Autowired
    private ShopItemService shopItemService;

    @Autowired
    private ShopItemRepository shopItemRepository;

    @GetMapping("")
    public List<ShopItem> getShopItems(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return shopItemService.listShopItems(page,limit);
    }

    @GetMapping("/category/{id}")
    public List<ShopItem> getShopItemsByCategoryId(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return shopItemService.listShopItemsByCategoryId(id,page,limit);
    }
    @GetMapping("/search/{name}")
    public List<ShopItem> searchShopItemByNameContaining(@PathVariable String name, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return shopItemRepository.findByNameContaining(name, PageRequest.of(page, limit)).getContent();
    }
    @GetMapping("/{id}")
    public Response getShopItemById(@PathVariable Long id) {
        try {
            return Response.createOKResponse(shopItemService.getShopItemById(id));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PostMapping({"", "/"})
    public Response saveShopItem(@RequestBody ShopItem shopItem) {
        try {
            return Response.createOKResponse(shopItemService.createShopItem(shopItem));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PutMapping("/{id}")
    public Response updateShopItem(@PathVariable Long id, @RequestBody ShopItem shopItem) {
        try {
            return Response.createOKResponse(shopItemService.updateShopItem(id, shopItem));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }
    //@PreAuthorize("hasAuthority('DELETE_AUTHORITY') or #id == principal.userId")
    @DeleteMapping("/{id}")
    public Response deleteShopItem(@PathVariable Long id) {
        try {
            shopItemService.deleteShopItem(id);
            return Response.createOKResponse("Successful delete");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

}


