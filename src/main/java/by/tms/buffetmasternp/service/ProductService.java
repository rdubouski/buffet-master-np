package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.dto.BoxItemDto;
import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        System.out.println(product);
        product.setStatus(Status.OPEN);
        productRepository.save(product);
    }

    public List<BoxItemDto> getAllBoxItemsDto() {
        List<Product> products = productRepository.findAll();
        List<BoxItemDto> boxItemDtos = new ArrayList<>();
        for (Product product : products) {
            BoxItemDto boxItemDto = new BoxItemDto();
            boxItemDto.setProductId(product.getId());
            boxItemDto.setProductName(product.getName());
            boxItemDto.setImageUrl(product.getImage());
            boxItemDtos.add(boxItemDto);
        }
        return boxItemDtos;
    }

}
