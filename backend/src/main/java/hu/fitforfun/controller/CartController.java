package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.request.TransactionItemRequestModel;
import hu.fitforfun.model.user.User;
import hu.fitforfun.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addToCart/{userId}")
    public User addItemToCart(@PathVariable Long userId, @RequestBody TransactionItemRequestModel item) {
        try {
            return cartService.addItemToCart(userId, item);
        } catch (FitforfunException e) {
            return null;
        }
    }

    @GetMapping("/{userId}/deleteFromCart/{itemId}")
    public Response deleteItemFromCar(@PathVariable Long userId, @PathVariable Long itemId) {
        try {
            cartService.deleteItemFromCart(userId, itemId);
            return Response.createOKResponse("successfully delete item from cart");
        } catch (FitforfunException e) {
            return Response.createErrorResponse("error during delete item from cart");
        }
    }

    @GetMapping("/{transactionItemId}/incrementTransactionItemQuantity")
    public Response incrementTransactionItemQuantity(@PathVariable Long transactionItemId) {
        try {
            return Response.createOKResponse(cartService.incrementTransactionItemQuantity(transactionItemId));
        } catch (FitforfunException e) {
            return Response.createErrorResponse("error during increment quantity");
        }
    }

    @GetMapping("/{transactionItemId}/decrementTransactionItemQuantity")
    public Response decrementTransactionItemQuantity(@PathVariable Long transactionItemId) {
        try {
            return Response.createOKResponse(cartService.decrementTransactionItemQuantity(transactionItemId));
        } catch (FitforfunException e) {
            return Response.createErrorResponse("error during decrement quantity");
        }
    }

}
