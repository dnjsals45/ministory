package seongmin.minilife.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.minilife.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
