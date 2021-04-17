package hu.fitforfun.repositories;

import hu.fitforfun.model.Comment;
import hu.fitforfun.model.shop.ShopItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    List<Comment> findByShopItemOrderByCreatedDesc(ShopItem shopItem);
}
