package yurii.karpliuk.marketplace.service;

import org.springframework.http.ResponseEntity;
import yurii.karpliuk.marketplace.dto.request.UserAddRequest;
import yurii.karpliuk.marketplace.entity.Product;
import yurii.karpliuk.marketplace.entity.User;
import yurii.karpliuk.marketplace.exception.NotEnoughMoneyException;
import yurii.karpliuk.marketplace.exception.NotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {
    Set<User> findAllUsers();

    ResponseEntity<?> createUser(UserAddRequest userAddRequest);

    ResponseEntity<?> buyProduct(Long userId, Long productId) throws NotFoundException, NotEnoughMoneyException;

    Set<Product> userProducts(Long userId) throws NotFoundException;

    ResponseEntity<?> deleteUser(Long userId) throws NotFoundException;
}
