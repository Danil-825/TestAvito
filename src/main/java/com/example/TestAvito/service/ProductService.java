package com.example.TestAvito.service;


import com.example.TestAvito.entity.Product;
import com.example.TestAvito.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;


    public List<Product> listProducts(String name) {
        if (name != null) return productRepository.findByName(name);
        return (List<Product>) productRepository.findAll();
    }


    public void saveProduct(Product product) {
        log.info("Saving product: {}", product);
        productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}
