package com.spring.redis;

import com.spring.redis.entity.Product;
import com.spring.redis.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class SpringDataRedisApplication {

    @Autowired
    private ProductDAO productDAO;

    @PostMapping()
    public Product save(@RequestBody Product product) {
        return productDAO.save(product);
    }

    @GetMapping
    public List<Product> allProducts() {
        return productDAO.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product", unless = "#result.price > 6000")
    public Product findProduct(@PathVariable("id") Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        return productDAO.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        return productDAO.deleteProduct(id);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRedisApplication.class);
    }
}
