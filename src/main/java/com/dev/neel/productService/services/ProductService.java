package com.dev.neel.productService.services;

import java.util.List;

import com.dev.neel.productService.dtos.GenericProductDto;
import com.dev.neel.productService.dtos.RequestProductDto;
import com.dev.neel.productService.exceptions.InvalidProductIdException;

public interface ProductService {
    
    public GenericProductDto createProduct(RequestProductDto product);
    
    public GenericProductDto updateProductById(Long id, RequestProductDto product);
    
    public GenericProductDto deleteProductById(Long id);
    
    public GenericProductDto getProductById(Long id) throws InvalidProductIdException;
    
    public List<GenericProductDto> getAllProducts();
    
    
}