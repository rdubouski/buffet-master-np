package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.dto.BoxDto;
import by.tms.buffetmasternp.dto.BoxItemDto;
import by.tms.buffetmasternp.entity.Account;
import by.tms.buffetmasternp.entity.Box;
import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.enums.Role;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.enums.Type;
import by.tms.buffetmasternp.repository.BoxItemRepository;
import by.tms.buffetmasternp.repository.BoxRepository;
import by.tms.buffetmasternp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private BoxItemRepository boxItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public BoxDto getBoxDtoById(Long id) {
        Box box = boxRepository.findById(id).orElse(null);
        BoxDto boxDto = new BoxDto();
        boxDto.setGroupBox(box.getGroupBox());
        boxDto.setImage(box.getImage());
        boxDto.setName(box.getName());
        boxDto.setDescription(box.getDescription());
        boxDto.setStatus(box.getStatus());
        boxDto.setType(box.getType());
        return boxDto;
    }
    
//    public BoxDto addBox(BoxDto boxDto, Authentication authentication) {
//        Box box = getBox(boxDto, authentication);
//        boxRepository.save(box);
//        for (BoxItem item: boxDto.getBoxItems()) {
//            BoxItem boxItem = new BoxItem();
//            boxItem.setBox(box);
//            boxItem.setProduct(item.getProduct());
//            boxItem.setQuantity(item.getQuantity());
//            boxItemRepository.save(boxItem);
//        }
//        return boxDto;
//    }

    public List<BoxItemDto> getBoxItemDtoByBox(List<BoxItemDto> boxItemDtos, Long productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            BoxItemDto boxItemDto = new BoxItemDto();
            boxItemDto.setProductId(product.getId());
            boxItemDto.setProductName(product.getName());
            boxItemDto.setImageUrl(product.getImage());
            boxItemDto.setQuantity(quantity);
            boxItemDtos.add(boxItemDto);
        } //добавить исключение
        return boxItemDtos;
    }

    public List<BoxItemDto> deleteBoxItemDtoByBox(List<BoxItemDto> boxItemDtos, Long productId) {
        for (int i = 0; i < boxItemDtos.size(); i++) {
            BoxItemDto boxItemDto = boxItemDtos.get(i);
            if (boxItemDto.getProductId().equals(productId)) {
                boxItemDtos.remove(boxItemDto);
            }
        }
        return boxItemDtos;
    }

    private static Box getBox(BoxDto boxDto, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Box box = new Box();
        box.setAccount(account);
        box.setName(boxDto.getName());
        box.setDescription(boxDto.getDescription());
        box.setGroupBox(boxDto.getGroupBox());
        box.setImage(boxDto.getImage());
        box.setStatus(Status.OPEN);
        if (account.getAuthorities().contains(Role.ROLE_ADMIN)) {
            box.setType(Type.DEFAULT);
        } else {
            box.setType(Type.CUSTOM);
        }
        return box;
    }

}
