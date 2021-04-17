package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.request.MessageRequestModel;
import hu.fitforfun.model.request.ShopItemRequestModel;
import hu.fitforfun.model.shop.ShopItem;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopItemService {
    ShopItem getShopItemById(Long id) throws FitforfunException;

    Iterable<ShopItem> listShopItems(int page, int limit, String filter, Specification<ShopItem> spec);

    Page<ShopItem> listShopItemsByCategoryId(Long categoryId, int page, int limit);

    ShopItem createShopItem(ShopItem shopItem) throws FitforfunException;

    void deleteShopItem(Long id) throws FitforfunException;

    ShopItem updateShopItem(Long id,ShopItem shopItem) throws FitforfunException;

    List<Comment> getComments(Long id);

    Comment addComment(Long id, MessageRequestModel massage);

    List<ShopItem> listShopItemsWithoutFilter();

    void addImage(Long id, MultipartFile multipartFile) throws Exception;


    }
