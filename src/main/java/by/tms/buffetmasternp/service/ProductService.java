package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.dto.BoxItemDto;
import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByStatus(Status status) {
        return productRepository.findAllByStatus(status);
    }

    public void save(Product product) {
        product.setStatus(Status.OPEN);
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new EntityNotFoundException("Закуска с id " + id + " не найдена");
        }
    }

    public void closeProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStatus(Status.CLOSED);
            productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Закуска с id " + id + " не найдена");
        }
    }

    public void editProduct(Product product) {
        Product newProduct = new Product();
        Product oldProduct = getProductById(product.getId());
        newProduct.setId(product.getId());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setStatus(oldProduct.getStatus());
        newProduct.setPrice(product.getPrice());
        newProduct.setImage(product.getImage());
        newProduct.setMin(product.getMin());
        newProduct.setIngredient(product.getIngredient());
        productRepository.save(newProduct);
    }

    public List<BoxItemDto> getAllBoxItemsDto(List<BoxItemDto> list) {
        List<Long> boxIds = new ArrayList<>();
        for (BoxItemDto boxItemDto : list) {
            boxIds.add(boxItemDto.getProductId());
        }
        if (boxIds.isEmpty()) {
            boxIds.add(0L);
        }
        List<Product> products = productRepository.findAllByStatusAndIdNotIn(Status.OPEN, boxIds);
        List<BoxItemDto> boxItemDtos = new ArrayList<>();
        for (Product product : products) {
            BoxItemDto boxItemDto = new BoxItemDto();
            boxItemDto.setProductId(product.getId());
            boxItemDto.setProductName(product.getName());
            boxItemDto.setProductDescription(product.getDescription());
            boxItemDto.setMin(product.getMin());
            boxItemDto.setPrice(product.getPrice());
            boxItemDto.setImageUrl(product.getImage());
            boxItemDtos.add(boxItemDto);
        }
        return boxItemDtos;
    }

}
