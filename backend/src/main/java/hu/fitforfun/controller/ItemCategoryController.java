package hu.fitforfun.controller;

import hu.fitforfun.model.shop.ItemCategory;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.repositories.ItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item-categories")
public class ItemCategoryController {

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @GetMapping("")
    public List<ItemCategory> getCategories() {
        return itemCategoryRepository.findAll();
    }
}
