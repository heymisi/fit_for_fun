package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.shop.ShopItem;

import java.util.List;

public interface ShopItemService {
    ShopItem getShopItemById(Long id) throws FitforfunException;

    List<ShopItem> listShopItems(int page, int limit);

    List<ShopItem> listShopItemsByCategoryId(Long categoryId, int page, int limit);

    ShopItem createShopItem(ShopItem shopItem) throws FitforfunException;

    void deleteShopItem(Long id) throws FitforfunException;

    ShopItem updateShopItem(Long id,ShopItem shopItem) throws FitforfunException;
}
