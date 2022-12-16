package yurii.karpliuk.marketplace.service;

import org.springframework.http.ResponseEntity;
import yurii.karpliuk.marketplace.dto.request.ProductAddRequest;
import yurii.karpliuk.marketplace.entity.Product;
import yurii.karpliuk.marketplace.entity.User;
import yurii.karpliuk.marketplace.exception.NotFoundException;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Set<Product> findAllProducts();
    ResponseEntity<?> createProduct(ProductAddRequest productAddRequest);

    Set<User> productUsers(Long productId) throws NotFoundException;

    ResponseEntity<?> deleteProduct(Long productId) throws NotFoundException;
}
