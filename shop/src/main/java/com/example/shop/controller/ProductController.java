package com.example.shop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.document.ProductDocument;
import com.example.shop.dto.ProductDTO;
import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    // localhost:8080/products 로만 끝낼 예정 + GET
    // 여기에 ?page=3&size=10 이런 형식일 예정
    @GetMapping
    public ResponseEntity<List<Product>> getProduct(@RequestParam(defaultValue = "1") int page, int size) {

        List<Product> products = productService.getProducts(page, size);
        return ResponseEntity.ok(products);

    }

    // localhost:8080/products 로만 끝낼 예정 + POST
    @PostMapping
    public ResponseEntity<Product> postMethodName(@RequestBody ProductDTO productDTO) {

        Product product = productService.createProduct(productDTO);

        return ResponseEntity.ok(product);

    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDocument>> getSearchProduct(@RequestParam String query,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "1000000000") double maxPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<ProductDocument> productDocuments = productService.searchProduct(query, category, minPrice, maxPrice, page,
                size);

        return ResponseEntity.ok(productDocuments);

    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getSuggestions(@RequestParam String query) {

        List<String> suggestions = productService.getSuggestions(query);

        return ResponseEntity.ok(suggestions);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();

    }

}
