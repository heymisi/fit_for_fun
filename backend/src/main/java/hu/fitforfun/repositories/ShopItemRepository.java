package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.ShopItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShopItemRepository extends PagingAndSortingRepository<ShopItem,Long> {
    Optional<ShopItem> findByName(String name);

    Page<ShopItem> findByCategoryId(Long id, Pageable pageable);

    Page<ShopItem> findByNameContaining(String name, Pageable pageable);
}
