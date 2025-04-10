package org.hakifiles.api.domain.controllers;

import jakarta.validation.Valid;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.Product;
import org.hakifiles.api.domain.services.ProductService;
import org.hakifiles.api.infrastructure.tools.Errors;
import org.hakifiles.api.infrastructure.utils.GoogleCloudStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    final GoogleCloudStorage googleCloudStorage;

    @Autowired
    public ProductController(GoogleCloudStorage googleCloudStorage) {
        this.googleCloudStorage = googleCloudStorage;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMultipleProducts(@Valid @RequestBody List<Product> products, BindingResult result) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        List<Product> productsDB = new ArrayList<>();

        for (Product product : products) {
            productsDB.add(productService.saveProduct(product));
        }

        return productsDB.isEmpty() ? ResponseEntity.badRequest().build() :
                ResponseEntity.status(HttpStatus.CREATED).body(productsDB);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addImageToCardInfo(@RequestBody MultipartFile image, @PathVariable Long id) throws IOException {
        Optional<Product> productById = productService.getProductById(id);
        if (productById.isPresent()) {
            Product product = productById.get();
            String url = googleCloudStorage.uploadImage(product.createUrl(), image.getBytes(), image.getContentType());
            product.setImg(url);
            return ResponseEntity.ok(productService.saveProduct(product));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editProduct(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        Optional<Product> productById = productService.getProductById(id);

        if (productById.isPresent()) {
            Product productDB = productById.get();
            productDB.setProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDB));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> productById = productService.getProductById(id);

        if (productById.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.ok(productById.get());
        }
        return ResponseEntity.notFound().build();
    }
}
