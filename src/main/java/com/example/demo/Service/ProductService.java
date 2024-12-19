package com.example.demo.Service;

import com.example.demo.DTO.ProductDto;
import com.example.demo.Entity.Product;
import com.example.demo.Exception.ProductNotFoundException;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // convert ProductDto to Product using Builder
    private Product convertDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .build();
    }

    // create
    public Product creatProduct(ProductDto productDto){
        return productRepository.save(convertDtoToProduct(productDto));
    }

    // Read
    public Product findProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
           throw new ProductNotFoundException("Product with ID " + id + " not found");
        }else {
            return product.get();
        }
    }

    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    public Product getOrCreateProductByName(String name, ProductDto productDto) {
        return productRepository.findByName(name)
                .orElseGet(() -> productRepository.save(convertDtoToProduct(productDto)));
    }

    // update
    public Product updateProduct(Long id, ProductDto productDto) {
        Product product = findProductById(id);
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        return productRepository.save(product);
    }

    // delete
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
