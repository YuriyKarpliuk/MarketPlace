package yurii.karpliuk.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yurii.karpliuk.marketplace.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
