package yurii.karpliuk.marketplace.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yurii.karpliuk.marketplace.dto.request.ProductAddRequest;
import yurii.karpliuk.marketplace.dto.response.MessageResponse;
import yurii.karpliuk.marketplace.entity.Product;
import yurii.karpliuk.marketplace.entity.User;
import yurii.karpliuk.marketplace.exception.NotFoundException;
import yurii.karpliuk.marketplace.repository.ProductRepository;
import yurii.karpliuk.marketplace.repository.UserRepository;
import yurii.karpliuk.marketplace.service.ProductService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Set<Product> findAllProducts() {
        List<Product> productList = productRepository.findAll();
        Set<Product> productSet = new HashSet<>(productList);
        return productSet;
    }

    @Override
    public ResponseEntity<?> createProduct(ProductAddRequest productAddRequest) {
        Product product = new Product();
        product.setName(productAddRequest.getName());
        product.setPrice(productAddRequest.getPrice());
        productRepository.save(product);
        return ResponseEntity.ok(new MessageResponse("Product created successfully!"));
    }

    @Override
    public Set<User> productUsers(Long productId) throws NotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product"));
        return product.getUsers();
    }
    @Override
    public  ResponseEntity<?>  deleteProduct(Long productId) throws NotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product"));
        Set<User> users = product.getUsers();
        for(User user:users){
            user.getProducts().remove(product);
            userRepository.save(user);
        }
        log.info("In deleteProduct ProductServiceImpl - delete product with  id: '{}'", productId);
        productRepository.delete(product);
        return ResponseEntity.ok(new MessageResponse("Product deleted successfully!"));
    }
}
