package f4.auth.domain.user.persist.repository;

import f4.auth.domain.user.persist.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);

  Page<User> findAll(Pageable pageable);


  @Query(value = "SELECT IF(account_number IS NULL, 1, 0) FROM user WHERE id = :userId", nativeQuery = true)
  int isLinked(Long userId);

  @Modifying
  @Query(value = "UPDATE user SET account_number = :accountNumber, updated_at = NOW() WHERE id = :userId", nativeQuery = true)
  void updateAccount(Long userId, String accountNumber);
}
