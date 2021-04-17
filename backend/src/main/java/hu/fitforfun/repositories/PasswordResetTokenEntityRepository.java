package hu.fitforfun.repositories;

import hu.fitforfun.model.user.PasswordResetTokenEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PasswordResetTokenEntityRepository  extends PagingAndSortingRepository<PasswordResetTokenEntity, Long> {
    PasswordResetTokenEntity findByToken(String token);
}
