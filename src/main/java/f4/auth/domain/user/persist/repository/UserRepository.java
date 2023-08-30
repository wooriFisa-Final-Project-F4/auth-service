package f4.auth.domain.user.persist.repository;

import f4.auth.domain.user.persist.entity.Users;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

  Optional<Users> findById(Long id);

  Optional<Users> findByEmail(String email);

  Page<Users> findAll(Pageable pageable);
}
