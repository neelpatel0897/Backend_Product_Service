package com.dev.neel.productService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestProductDto {
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
}