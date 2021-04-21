package hu.fitforfun.bo;

import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.shop.ShopItem;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SportTypeBO {
    private String name;
    private List<SportFacility> sportFacilities;
    private List<Instructor> instructors;
    private Set<ShopItem> items;
}
