package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.Image;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.repositories.ShopItemRepository;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.services.ShopItemService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/shop-items")
public class ShopItemController {

    @Autowired
    private ShopItemService shopItemService;

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "")
    public Page<ShopItem> getShopItems(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "limit", defaultValue = "10") int limit,
                                       @RequestParam(value = "filter", defaultValue = "null") String filter,
                                       @And({@Spec(path = "sportType.name", params = "sport", spec = Like.class),
                                               @Spec(path = "category.categoryName", params = "category", spec = Like.class),
                                               @Spec(path = "price", params = {"priceMin", "priceMax"}, spec = Between.class)}) Specification<ShopItem> spec) {


        return shopItemService.listShopItems(page, limit, filter, spec);
    }

    @GetMapping(value = "/without-filters")
    public List<ShopItem> getShopItemsWithoutFilters() {
        return shopItemService.listShopItemsWithoutFilter();
    }


    @GetMapping("/search/{keyword}")
    public Page<ShopItem> searchShopItemByNameContaining
            (@PathVariable String keyword,
             @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return shopItemRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, limit));
    }

    @GetMapping("/{id}")
    public Response getShopItemById(@PathVariable Long id) {
        try {
            return Response.createOKResponse(shopItemService.getShopItemById(id));
        } catch (FitforfunException | IOException e) {
            return Response.createErrorResponse("error get item");
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

    @DeleteMapping("/{id}")
    public Response deleteShopItem(@PathVariable Long id) {
        try {
            shopItemService.deleteShopItem(id);
            return Response.createOKResponse("Successful delete");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable Long id) {
        return shopItemService.getComments(id);
    }

    @PostMapping("/{id}/addComment")
    public Response addComment(@PathVariable Long id, @RequestBody CommentRequestModel massage) {
        try {
            return Response.createOKResponse(shopItemService.addComment(id, massage));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PostMapping("/{id}/uploadImage")
    public Response uploadImage(@PathVariable Long id, @RequestParam("imageFile") MultipartFile file) {
        try {
            shopItemService.addImage(id, file);
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


