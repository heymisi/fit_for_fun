package hu.fitforfun.model.rating;

import com.fasterxml.jackson.databind.ser.Serializers;
import hu.fitforfun.model.BaseEntity;

import java.util.List;

public interface Rateable<T extends BaseEntity, R extends Rating> {
    List<R> getRatings();
    T addRating(R r);
}
