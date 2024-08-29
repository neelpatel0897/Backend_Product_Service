package com.dev.neel.productService.contollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    
    
    @GetMapping("/category/{name}")
    public void  getCategory(@RequestParam("name") String name) {
        System.out.println("Category");
    }

    @GetMapping("/categories")
    public void getAllCategories() {
        System.out.println("All Category");
    }
    
}