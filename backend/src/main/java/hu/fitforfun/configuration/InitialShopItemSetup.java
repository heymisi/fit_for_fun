package hu.fitforfun.configuration;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.shop.ItemCategory;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.repositories.ItemCategoryRepository;
import hu.fitforfun.services.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
@Component
public class InitialShopItemSetup {
    @Autowired
    ShopItemService shopItemService;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ItemCategory category1 = new ItemCategory();
        category1.setCategoryName("shoe");
        itemCategoryRepository.save(category1);

        ItemCategory category2 = new ItemCategory();
        category2.setCategoryName("accessories");
        itemCategoryRepository.save(category2);

        ShopItem item1 = new ShopItem();
        item1.setUnitsInStock(10);
        item1.setCategory(category1);
        item1.setDescription("good product");
        item1.setName("airjordar");
        item1.setPrice(new BigDecimal("100"));
        item1.setImageUrl("assets\\images\\products\\placeholder.png");

        ShopItem item2 = new ShopItem();
        item2.setUnitsInStock(10);
        item2.setCategory(category2);
        item2.setDescription("good product");
        item2.setName("nike");
        item2.setPrice(new BigDecimal("100"));
        item2.setImageUrl("assets\\images\\products\\placeholder.png");
        try {
            shopItemService.createShopItem(item1);
            shopItemService.createShopItem(item2);
        } catch (FitforfunException e) {
            System.err.println("itt nem jo tess");
            e.printStackTrace();
        }
    }
}
