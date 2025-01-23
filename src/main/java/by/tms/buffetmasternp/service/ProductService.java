package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.dto.BoxItemDto;
import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        System.out.println(product);
        product.setStatus(Status.OPEN);
        productRepository.save(product);
    }

    public List<BoxItemDto> getAllBoxItemsDto(List<BoxItemDto> list) {
        List<Long> boxIds = new ArrayList<>();
        for (BoxItemDto boxItemDto : list) {
            boxIds.add(boxItemDto.productId);
        }
        if (boxIds.isEmpty()) {
            boxIds.add(0L);
        }
        List<Product> products = productRepository.findAllByIdNotIn(boxIds);
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
