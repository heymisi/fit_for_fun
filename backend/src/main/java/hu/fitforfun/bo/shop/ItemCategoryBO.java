package hu.fitforfun.bo.shop;

import hu.fitforfun.model.shop.ShopItem;
import lombok.Data;

import java.util.Set;

@Data
public class ItemCategoryBO {
    private String categoryName;
    private Set<ShopItemBO> items;
}
