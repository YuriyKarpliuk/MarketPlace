package yurii.karpliuk.marketplace.service.Impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yurii.karpliuk.marketplace.dto.request.UserAddRequest;
import yurii.karpliuk.marketplace.dto.response.MessageResponse;
import yurii.karpliuk.marketplace.entity.Product;
import yurii.karpliuk.marketplace.entity.User;
import yurii.karpliuk.marketplace.exception.NotEnoughMoneyException;
import yurii.karpliuk.marketplace.exception.NotFoundException;
import yurii.karpliuk.marketplace.repository.ProductRepository;
import yurii.karpliuk.marketplace.repository.UserRepository;
import yurii.karpliuk.marketplace.service.UserService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Set<User> findAllUsers() {
        List<User> userList = userRepository.findAll();
        Set<User> userSet = new HashSet<>(userList);
        return userSet;
    }

    @Override
    public ResponseEntity<?> createUser(UserAddRequest userAddRequest) {
        User user = new User();
        user.setFirstName(userAddRequest.getFirstName());
        user.setLastName(userAddRequest.getLastName());
        user.setAmountOfMoney(userAddRequest.getAmountOfMoney());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User created successfully!"));
    }

    @Override
    public ResponseEntity<?> buyProduct(Long userId, Long productId) throws NotFoundException, NotEnoughMoneyException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product"));
        if (enoughMoney(user.getAmountOfMoney(), product.getPrice())) {
            user.getProducts().add(product);
            product.getUsers().add(user);
            user.setAmountOfMoney(user.getAmountOfMoney().subtract(productRepository.findById(productId).get().getPrice()));
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Product bought successfully!"));
        } else {
            log.info("In buyProduct UserServiceImpl - error: user with id:'{}' doesn't have enough money to buy product with id:", userId, productId);
            throw new NotEnoughMoneyException();
        }

    }

    private boolean enoughMoney(BigDecimal amountOfMoney, BigDecimal price) {
        if (amountOfMoney.subtract(price).compareTo(BigDecimal.valueOf(0)) == 0 || amountOfMoney.subtract(price).compareTo(BigDecimal.valueOf(0)) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<Product> userProducts(Long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User"));
        return user.getProducts();
    }

    @Override
    public  ResponseEntity<?> deleteUser(Long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User"));
        log.info("In deleteUser UserServiceImpl - delete user with  id: '{}'", userId);
        userRepository.delete(user);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

}
