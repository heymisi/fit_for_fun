package hu.fitforfun.bo;

import hu.fitforfun.bo.facility.SportFacilityBO;
import hu.fitforfun.bo.instructor.InstructorBO;
import hu.fitforfun.bo.shop.ShopItemBO;

import java.util.Date;

public class CommentBO {
    private String commenterName;
    private Date created;
    private String text;
    private SportFacilityBO sportFacility;
    private InstructorBO instructor;
    private ShopItemBO shopItem;
}
