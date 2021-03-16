package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopItemRepository extends JpaRepository<ShopItem,Long> {
}
