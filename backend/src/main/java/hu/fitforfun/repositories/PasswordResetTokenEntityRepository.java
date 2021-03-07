package hu.fitforfun.repositories;

import hu.fitforfun.model.OpeningHours;
import hu.fitforfun.model.PasswordResetTokenEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PasswordResetTokenEntityRepository  extends PagingAndSortingRepository<PasswordResetTokenEntity, Long> {
    PasswordResetTokenEntity findByToken(String token);
}
