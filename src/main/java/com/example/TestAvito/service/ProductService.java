package com.example.TestAvito.service;


import com.example.TestAvito.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private long ID = 0;

    {
        products.add(new Product(++ID, "Dendy", "Simple description", 10000, "Moskau", "Alex"));
        products.add(new Product(++ID, "Xiaomy ", "Simple description", 20000, "Krasnodar", "Tolik"));
    }
    public List<Product> listProducts() {return products;}
    public void saveProduct(Product product) {
        product.setId(++ID);
        products.add(product);
    }
    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    public Product getProductById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) return product;

        }
        return null;
    }

}
