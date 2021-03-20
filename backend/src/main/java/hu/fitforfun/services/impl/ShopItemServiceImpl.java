package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.repositories.ItemCategoryRepository;
import hu.fitforfun.repositories.ShopItemRepository;
import hu.fitforfun.services.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopItemServiceImpl implements ShopItemService {

    @Autowired
    ShopItemRepository shopItemRepository;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Override
    public ShopItem getShopItemById(Long id) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);
        }
        return optionalShopItem.get();
    }

    @Override
    public List<ShopItem> listShopItems(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<ShopItem> shopItems = shopItemRepository.findAll(pageableRequest);
        List<ShopItem> returnValue = shopItems.getContent();
        return returnValue;
    }

    @Override
    public List<ShopItem> listShopItemsByCategoryId(Long categoryId, int page, int limit) {
        if (page > 0) page--;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<ShopItem> shopItems = shopItemRepository.findByCategoryId(categoryId, pageableRequest);
        return shopItems.getContent();
    }

    @Override
    public ShopItem createShopItem(ShopItem shopItem) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findByName(shopItem.getName());
        if (optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_ALREADY_EXISTS);
        }
        return shopItemRepository.save(shopItem);
    }

    @Override
    public void deleteShopItem(Long id) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_ALREADY_EXISTS);
        }
        shopItemRepository.delete(optionalShopItem.get());
    }

    @Override
    public ShopItem updateShopItem(Long id, ShopItem shopItem) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_ALREADY_EXISTS);
        }
        ShopItem updatedShopItem = optionalShopItem.get();
        if (shopItem.getName() != null) {
            updatedShopItem.setName(shopItem.getName());
        }
        if (shopItem.getCategory() != null) {
            updatedShopItem.setCategory(shopItem.getCategory());
        }
        if (shopItem.getDescription() != null) {
            updatedShopItem.setDescription(shopItem.getDescription());
        }
        /*if(shopItem.getAvailableSizes() != null){
            updatedShopItem.setAvailableSizes(shopItem.getAvailableSizes());
        }*/
        if (shopItem.getPrice() != null) {
            updatedShopItem.setPrice(shopItem.getPrice());
        }
        if (shopItem.getUnitsInStock() != null) {
            updatedShopItem.setUnitsInStock(shopItem.getUnitsInStock());
        }
        return shopItemRepository.save(updatedShopItem);
    }
}
