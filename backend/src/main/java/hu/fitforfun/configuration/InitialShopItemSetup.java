package hu.fitforfun.configuration;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.SportType;
import hu.fitforfun.model.shop.ItemCategory;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.shop.TransactionItem;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.ShopItemService;
import hu.fitforfun.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class InitialShopItemSetup {
    @Autowired
    ShopItemService shopItemService;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TransactionItemRepository transactionItemRepository;

    @Autowired
    ShopItemRepository shopItemRepository;


    @Autowired
    SportTypeRepository sportTypeRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) throws Exception {
        ItemCategory category1 = new ItemCategory();
        category1.setCategoryName("Cipők");
        itemCategoryRepository.save(category1);

        ItemCategory category3 = new ItemCategory();
        category3.setCategoryName("kiegészítők");
        itemCategoryRepository.save(category3);

        ItemCategory category2 = new ItemCategory();
        category2.setCategoryName("Sport kellékek");
        itemCategoryRepository.save(category2);

        ItemCategory category4 = new ItemCategory();
        category4.setCategoryName("ruhák");
        itemCategoryRepository.save(category4);

        ShopItem shopItem1 = null;
        SportType sportType = new SportType();
        sportType.setName("Labdarugás");
        sportTypeRepository.save(sportType);

        SportType sportType2 = new SportType();
        sportType2.setName("Kosárlabda");
        sportTypeRepository.save(sportType2);

        SportType sportType3 = new SportType();
        sportType3.setName("Testépítés");
        sportTypeRepository.save(sportType3);


        SportType sportType222 = new SportType();
        sportType222.setName("Szabadidős sport");
        sportTypeRepository.save(sportType222);


        SportType running = new SportType();
        running.setName("Futás");
        sportTypeRepository.save(running);
        List<SportType> sportTypes = new ArrayList<>();
        sportTypeRepository.findAll().forEach(sportTypes::add);
        try {
            shopItem1 = shopItemService.createShopItem(create("item1","100",category3, sportType2));
            shopItemService.createShopItem(create("item2","101",category3,sportType3));
            shopItemService.createShopItem(create("item23","102",category3,sportType));
            shopItemService.createShopItem(create("item24","103",category1,sportType));
            shopItemService.createShopItem(create("item25","104",category1,sportType2));
            shopItemService.createShopItem(create("item26","105",category1,sportType3));
            shopItemService.createShopItem(create("item27","106",category1,sportType));
            shopItemService.createShopItem(create("item277","107",category1,sportType));
            shopItemService.createShopItem(create("iteasdm2","101",category3,sportType3));
            shopItemService.createShopItem(create("itevdm23","102",category3,sportType));
            shopItemService.createShopItem(create("itvxcasdem24","103",category1,sportType));
            shopItemService.createShopItem(create("itasdascxycem25","104",category1,sportType2));
            shopItemService.createShopItem(create("iteasdasdcm26","105",category1,sportType3));
            shopItemService.createShopItem(create("ityxcyxem27","106",category1,sportType));
            shopItemService.createShopItem(create("itvem277","107",category1,sportType));
            shopItemService.createShopItem(create("itbvcbem2","101",category3,sportType3));
            shopItemService.createShopItem(create("itvncem23","102",category3,sportType));
            shopItemService.createShopItem(create("itncvnem24","103",category1,sportType));
            shopItemService.createShopItem(create("itcvnbvnem25","104",category1,sportType2));
            shopItemService.createShopItem(create("itecncvm26","105",category1,sportType3));
            shopItemService.createShopItem(create("itecvbnm27","106",category1,sportType));
            shopItemService.createShopItem(create("itr3e1em277","107",category1,sportType));
            shopItemService.createShopItem(create("it2ee12em2","101",category3,sportType3));
            shopItemService.createShopItem(create("it21em23","102",category3,sportType));
            shopItemService.createShopItem(create("i1eem24","103",category1,sportType));
            shopItemService.createShopItem(create("ietem25","104",category1,sportType2));
            shopItemService.createShopItem(create("ieasd21tem26","105",category1,sportType3));
            shopItemService.createShopItem(create("itbnmem27","106",category1,sportType));
            shopItemService.createShopItem(create("ite,mvm277","107",category1,sportType));


        } catch (FitforfunException e) {
            System.err.println("itt nem jo tess");
            e.printStackTrace();
        }

        Comment comment1 = new Comment();
        comment1.setCommenterName("Misi");
        comment1.setText("Ez az én kiváló kommentem");
        commentRepository.save(comment1);
        Comment comment2 = new Comment();
        comment2.setCommenterName("Gyula");
        comment2.setText("Ez az én kiváló kommentem");
        commentRepository.save(comment2);

        shopItem1.addComment(comment1);
        shopItem1.addComment(comment2);

        shopItemRepository.save(shopItem1);
    }
    private ShopItem create(String name, String price, ItemCategory category2, SportType sportType){
        ShopItem item8 = new ShopItem();
        item8.setUnitsInStock(10);
        item8.setCategory(category2);
        item8.setDescription("good product good productgood productgood productgood productgood productgood productgood productgood");
        item8.setName(name);
        item8.setPrice(new BigDecimal(price));
        item8.setRating(ThreadLocalRandom.current().nextInt(0, 5 + 1));
        item8.setSportType(sportType);
        return item8;
    }

    private Transaction createTransaction(User user, List<ShopItem> shopItems) throws FitforfunException {
        TransactionItem item = new TransactionItem();
       // item.setShopItem(shopItems.get(0));
        item.setQuantity(2);
        transactionItemRepository.save(item);

        Transaction transaction = new Transaction();
        transaction.setPurchaser(user);
        transaction.addItem(item);
        transactionService.createTransaction(transaction);
        return transaction;
    }
}
