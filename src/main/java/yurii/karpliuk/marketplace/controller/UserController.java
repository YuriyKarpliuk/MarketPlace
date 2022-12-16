package yurii.karpliuk.marketplace.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yurii.karpliuk.marketplace.dto.request.UserAddRequest;
import yurii.karpliuk.marketplace.entity.Product;
import yurii.karpliuk.marketplace.entity.User;
import yurii.karpliuk.marketplace.exception.NotEnoughMoneyException;
import yurii.karpliuk.marketplace.exception.NotFoundException;
import yurii.karpliuk.marketplace.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserAddRequest userAddRequest) {
        log.info("In createUser UserController - user is created  with first name: '{}', last name: '{}'", userAddRequest.getFirstName(), userAddRequest.getLastName());
        return userService.createUser(userAddRequest);
    }

    @GetMapping("/getAll")
    public Set<User> findAllUsers() {
        log.info("In findAllUsers UserController - output all users");
        return userService.findAllUsers();
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyProduct(@Positive  @RequestParam Long userId, @Positive @RequestParam Long productId)throws NotFoundException, NotEnoughMoneyException {
        log.info("In buyProduct UserController - user with id: '{}' bought product with id: '{}'", userId, productId);
        return userService.buyProduct(userId, productId);
    }
    @GetMapping("/getUserProducts")
    public Set<Product> getUserProducts(@Positive @RequestParam Long userId)throws NotFoundException {
        log.info("In getUserProducts UserController - output all user products");
        return userService.userProducts(userId);
    }
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?>  deleteUser(@Positive @PathVariable Long id)throws NotFoundException{
        log.info("In deleteUser UserController - deleted user with id:'{}'",id);
        return userService.deleteUser(id);
    }
}
