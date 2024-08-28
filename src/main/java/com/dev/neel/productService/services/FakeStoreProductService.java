package com.dev.neel.productService.services;

import java.util.ArrayList;
import java.util.List;


import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;

import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.dev.neel.productService.dtos.FakeStoreProductDto;
import com.dev.neel.productService.dtos.GenericProductDto;
import com.dev.neel.productService.dtos.RequestProductDto;
import com.dev.neel.productService.exceptions.InvalidProductIdException;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;
    private String specificProductRequestUrl = "https://fakestoreapi.com/products/";

    private String productRequestsBaseUrl = "https://fakestoreapi.com/products";

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate; 
    }


    @Override
    public GenericProductDto createProduct(RequestProductDto product) {

        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(productRequestsBaseUrl, product, FakeStoreProductDto.class);



        return convertFakeStoreProductDto(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto updateProductById(Long id, RequestProductDto product) {
        String productUrl = specificProductRequestUrl+id;      
       

        RequestCallback requestCallback = restTemplate.httpEntityCallback(product, FakeStoreProductDto.class);
		HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
				
		 FakeStoreProductDto fakeStoreProductDto = restTemplate.execute(productUrl, HttpMethod.PUT, requestCallback, responseExtractor, id);

         return convertFakeStoreProductDto(fakeStoreProductDto);
        
    }

    @Override
    public GenericProductDto deleteProductById(Long id) {


        String productUrl = specificProductRequestUrl+id;      
       

        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
		HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
				new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
         FakeStoreProductDto fakeStoreProductDto= restTemplate.execute(productUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);

         return convertFakeStoreProductDto(fakeStoreProductDto);

    }

    @Override
    public GenericProductDto getProductById(Long id) throws InvalidProductIdException {
    
        String productUrl = specificProductRequestUrl+id;
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(productUrl,FakeStoreProductDto.class );
    
        if( fakeStoreProductDto == null) {
            throw new InvalidProductIdException(id, "Invalid productId passed");
        }
    
        return convertFakeStoreProductDto(fakeStoreProductDto);
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> response=restTemplate.getForEntity(productRequestsBaseUrl,FakeStoreProductDto[].class);

        FakeStoreProductDto[] products=response.getBody();
        List<GenericProductDto> result=new ArrayList<>();
        for(FakeStoreProductDto productDto:products){               

                result.add(convertFakeStoreProductDto(productDto));
        }

        return result;
    }
    

    private GenericProductDto convertFakeStoreProductDto(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto genericProductDto = new GenericProductDto();
        
        genericProductDto.setId(fakeStoreProductDto.getId());
        genericProductDto.setTitle(fakeStoreProductDto.getTitle());
        genericProductDto.setPrice(fakeStoreProductDto.getPrice());
        genericProductDto.setDescription(fakeStoreProductDto.getDescription());
        genericProductDto.setImage(fakeStoreProductDto.getImage());
        genericProductDto.setCategory(fakeStoreProductDto.getCategory());

        return genericProductDto;


    }

}