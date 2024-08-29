package com.dev.neel.productService.contollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.neel.productService.dtos.GenericProductDto;
import com.dev.neel.productService.dtos.RequestProductDto;
import com.dev.neel.productService.exceptions.InvalidProductIdException;
import com.dev.neel.productService.services.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    

    ProductService productService;

    public ProductController(@Qualifier("fakeStoreProductService")ProductService productService) {
        this.productService = productService;
    }

    
    @GetMapping()
    public ResponseEntity<List<GenericProductDto>> getProducts() {
        return new ResponseEntity<>( productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericProductDto> getProductById(@PathVariable("id") Long id) throws InvalidProductIdException {
        GenericProductDto product = productService.getProductById(id);
        return new ResponseEntity<>(product , HttpStatus.OK);        
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericProductDto> updateProductById(@PathVariable("id") Long id, @RequestBody RequestProductDto requestProductDto) throws InvalidProductIdException {
        GenericProductDto product = productService.updateProductById(id, requestProductDto);
        return new ResponseEntity<>(product , HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable("id") Long id) throws InvalidProductIdException {
        GenericProductDto product = productService.deleteProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericProductDto> createProduct(@RequestBody RequestProductDto requestProductDto){
        try {
            GenericProductDto product = productService.createProduct(requestProductDto);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
//Adding comment
