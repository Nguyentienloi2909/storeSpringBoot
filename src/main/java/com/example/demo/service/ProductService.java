package com.example.demo.service;



import com.example.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> getAllProduct();
    public Product findById(int id);
    public Product addProduct(Product product);
    public Boolean updateProduct(Product product);
    public Boolean deleteProductById(int id);
    public List<Product> getAllProductByProductName(String productName);

}
