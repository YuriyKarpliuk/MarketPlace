package yurii.karpliuk.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yurii.karpliuk.marketplace.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
