package yurii.karpliuk.marketplace.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yurii.karpliuk.marketplace.dto.request.ProductAddRequest;
import yurii.karpliuk.marketplace.entity.Product;
import yurii.karpliuk.marketplace.entity.User;
import yurii.karpliuk.marketplace.exception.NotFoundException;
import yurii.karpliuk.marketplace.service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductAddRequest productAddRequest) {
        log.info("In createProduct ProductController - product is created  with  name: '{}',  price: '{}'", productAddRequest.getName(), productAddRequest.getPrice());
        return productService.createProduct(productAddRequest);
    }

    @GetMapping("/getAll")
    public Set<Product> findAllProducts() {
        log.info("In findAllProducts ProductController - output all products");
        return productService.findAllProducts();
    }

    @GetMapping("/getProductUsers")
    public Set<User> getProductUsers(@Positive @RequestParam Long productId) throws NotFoundException {
        log.info("In getProductUsers ProductController - output all product users");
        return productService.productUsers(productId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@Positive @PathVariable Long id)  throws NotFoundException{
        log.info("In deleteProduct ProductController - deleted product with id:'{}'",id);
        return productService.deleteProduct(id);
    }
}
