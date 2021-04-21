package hu.fitforfun.repositories;

import hu.fitforfun.model.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByContactDataEmail(String email);
    User findUserByEmailVerificationToken(String token);
}
