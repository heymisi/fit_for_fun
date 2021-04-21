package hu.fitforfun.bo.shop;

import hu.fitforfun.bo.CommentBO;
import hu.fitforfun.bo.ImageBO;
import hu.fitforfun.bo.SportTypeBO;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ShopItemBO {
    private String name;
    private BigDecimal price;
    private Date dateCreated;
    private Date lastUpdated;
    private Integer unitsInStock;
    private String description;
    private String imageUrl;
    private ItemCategoryBO category;
    private SportTypeBO sportType;
    private List<CommentBO> comments;
    private ImageBO image;
}
