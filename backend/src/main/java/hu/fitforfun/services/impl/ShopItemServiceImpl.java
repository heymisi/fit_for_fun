package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.Image;
import hu.fitforfun.model.request.MessageRequestModel;
import hu.fitforfun.model.request.ShopItemRequestModel;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.repositories.CommentRepository;
import hu.fitforfun.repositories.ItemCategoryRepository;
import hu.fitforfun.repositories.ShopItemRepository;
import hu.fitforfun.services.ShopItemService;
import hu.fitforfun.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ShopItemServiceImpl implements ShopItemService {

    @Autowired
    ShopItemRepository shopItemRepository;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public ShopItem getShopItemById(Long id) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);
        }
        return optionalShopItem.get();
    }

    @Override
    public Iterable<ShopItem> listShopItems(int page, int limit, String filter, Specification<ShopItem> spec) {
        Pageable pageableRequest = PageRequest.of(page, limit);

        if (filter.equals("priceBackward"))
            pageableRequest = PageRequest.of(page, limit, Sort.by("price").descending());

        else if (filter.equals("priceForward"))
            pageableRequest = PageRequest.of(page, limit, Sort.by("price").ascending());

        else if (filter.equals("byRate"))
            pageableRequest = PageRequest.of(page, limit, Sort.by("rating").descending());

        else if (filter.equals("newest"))
            pageableRequest = PageRequest.of(page, limit, Sort.by("dateCreated").descending());


        return shopItemRepository.findAll(spec, pageableRequest);
    }

    public List<ShopItem> listShopItemsWithoutFilter() {
        List<ShopItem> returnValue = new ArrayList<>();
        shopItemRepository.findAll().forEach(returnValue::add);
        return returnValue;
    }

    @Override
    public Page<ShopItem> listShopItemsByCategoryId(Long categoryId, int page, int limit) {
        System.err.println(page + limit);
        Pageable pageableRequest = PageRequest.of(page, limit);
        return shopItemRepository.findByCategoryId(categoryId, pageableRequest);
    }

    @Override
    public ShopItem createShopItem(ShopItem shopItem) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findByName(shopItem.getName());
        if (optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_ALREADY_EXISTS);
        }

        shopItem.setComments(new ArrayList());
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

    @Override
    public List<Comment> getComments(Long id) {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            return null;
        }
        ShopItem shopItem = optionalShopItem.get();
        return commentRepository.findByShopItemOrderByCreatedDesc(shopItem);
    }

    @Override
    public Comment addComment(Long id, MessageRequestModel massage) {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            return null;
        }
        ShopItem shopItem = optionalShopItem.get();
        Comment comment = new Comment();
        comment.setText(massage.getMessage());
        comment.setCommenterName("TODO");
        commentRepository.save(comment);
        shopItem.addComment(comment);
        shopItemRepository.save(shopItem);
        return comment;
    }

    @Override
    public void addImage(Long id, MultipartFile multipartFile) throws Exception {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);
        }
        ShopItem shopitem = optionalShopItem.get();
        shopitem.setImage(new Image(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                ImageUtils.compressBytes(multipartFile.getBytes())));
        shopItemRepository.save(shopitem);
    }

}
