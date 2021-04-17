package hu.fitforfun.model.request;

import hu.fitforfun.model.SportType;
import hu.fitforfun.model.shop.ItemCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ShopItemRequestModel {
    private String name;
    private BigDecimal price;
    private Integer unitsInStock;
    private String description;
  //  private ItemCategory category;
  //  private SportType sportType;
    private MultipartFile file;
  //  private Integer Rating;
}
