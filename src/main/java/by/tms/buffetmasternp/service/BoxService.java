package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.dto.BoxDto;
import by.tms.buffetmasternp.dto.BoxItemDto;
import by.tms.buffetmasternp.entity.Account;
import by.tms.buffetmasternp.entity.Box;
import by.tms.buffetmasternp.entity.BoxItem;
import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.enums.Role;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.enums.Type;
import by.tms.buffetmasternp.repository.BoxItemRepository;
import by.tms.buffetmasternp.repository.BoxRepository;
import by.tms.buffetmasternp.repository.GroupBoxRepository;
import by.tms.buffetmasternp.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

    private final BoxRepository boxRepository;
    private final BoxItemRepository boxItemRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final GroupBoxRepository groupBoxRepository;

    public BoxService(BoxRepository boxRepository, BoxItemRepository boxItemRepository,
                      ProductRepository productRepository, ProductService productService, GroupBoxRepository groupBoxRepository) {
        this.boxRepository = boxRepository;
        this.boxItemRepository = boxItemRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.groupBoxRepository = groupBoxRepository;
    }

    public List<BoxDto> getAllBoxesByStatusAndType(Status status, Type type) {
        List<Box> boxes = boxRepository.findAllByStatusAndType(status, type);
        return getBoxDtos(boxes);
    }

    public List<BoxDto> convertBoxesToBoxDtos(List<Box> boxes) {
        return getBoxDtos(boxes);
    }

    private List<BoxDto> getBoxDtos(List<Box> boxes) {
        List<BoxDto> boxDtos = new ArrayList<>();
        for (Box box : boxes) {
            List<BoxItem> boxItems = boxItemRepository.findAllByBoxId(box.getId());
            List<BoxItemDto> boxItemDtos = productService.getAllBoxItemsDtoIn(boxItems);
            BoxDto boxDto = new BoxDto();
            boxDto.setId(box.getId());
            boxDto.setName(box.getName());
            boxDto.setStatus(box.getStatus());
            boxDto.setType(box.getType());
            boxDto.setDescription(box.getDescription());
            boxDto.setGroupBox(box.getGroupBox());
            boxDto.setImage(box.getImage());
            boxDto.setBoxItemDtos(boxItemDtos);
            boxDto.setPrice(getPriceBox(boxItemDtos));
            boxDtos.add(boxDto);
        }
        return boxDtos;
    }

    public Box getBoxById(Long id) {
        Optional<Box> box = boxRepository.findById(id);
        if (box.isPresent()) {
            return box.get();
        } else {
            throw new EntityNotFoundException("Бокс с id " + id + " не найден");
        }
    }

    public void editBox(Box box) {
        Box newBox = new Box();
        Box oldBox = getBoxById(box.getId());
        newBox.setId(box.getId());
        newBox.setName(box.getName());
        newBox.setDescription(box.getDescription());
        newBox.setImage(box.getImage());
        newBox.setAccount(oldBox.getAccount());
        newBox.setStatus(oldBox.getStatus());
        newBox.setType(oldBox.getType());
        newBox.setGroupBox(oldBox.getGroupBox());
        boxRepository.save(newBox);
    }

    public BoxDto getBoxDtoById(Long id) {
        Box box;
        BoxDto boxDto;
        Optional<Box> optionalBox = boxRepository.findById(id);
        if (optionalBox.isPresent()) {
            box = optionalBox.get();
            List<BoxItem> boxItems = boxItemRepository.findAllByBoxId(box.getId());
            List<BoxItemDto> boxItemDtos = productService.getAllBoxItemsDtoIn(boxItems);
            boxDto = new BoxDto();
            boxDto.setId(box.getId());
            boxDto.setName(box.getName());
            boxDto.setStatus(box.getStatus());
            boxDto.setType(box.getType());
            boxDto.setDescription(box.getDescription());
            boxDto.setGroupBox(box.getGroupBox());
            boxDto.setImage(box.getImage());
            boxDto.setBoxItemDtos(boxItemDtos);
            boxDto.setPrice(getPriceBox(boxItemDtos));
        } else {
            throw new EntityNotFoundException("Бокс с id " + id + " не найден");
        }
        return boxDto;
    }

    public double getPriceCart(List<BoxDto> boxDtos) {
        double price = 0;
        for (BoxDto boxDto : boxDtos) {
            price += boxDto.getPrice();
        }
        return price;
    }

    public double getPriceBox(List<BoxItemDto> boxItemDtoList) {
        double price = 0;
        for (BoxItemDto boxItemDto : boxItemDtoList) {
            price += boxItemDto.getPrice() * boxItemDto.getQuantity();
        }
        return price;
    }

    @Transactional
    public void addBox(BoxDto boxDto, Authentication authentication) {
        Box box = getBox(boxDto, authentication);
        boxRepository.save(box);
        for (BoxItemDto item: boxDto.getBoxItemDtos()) {
            BoxItem boxItem = new BoxItem();
            boxItem.setBox(box);
            boxItem.setProduct(productRepository.findById(item.getProductId()).get());
            boxItem.setQuantity(item.getQuantity());
            boxItemRepository.save(boxItem);
        }
    }

    @Transactional
    public BoxDto addBoxUser(BoxDto boxDto, Authentication authentication) {
        boxDto.setGroupBox(groupBoxRepository.findById(9L).get());
        boxDto.setImage("");
        String description = "";
        Box box = getBox(boxDto, authentication);
        boxRepository.save(box);
        for (BoxItemDto item: boxDto.getBoxItemDtos()) {
            BoxItem boxItem = new BoxItem();
            boxItem.setBox(box);
            boxItem.setProduct(productRepository.findById(item.getProductId()).get());
            boxItem.setQuantity(item.getQuantity());
            description = description.concat(item.getProductName())
                                .concat(" - ")
                                .concat(String.valueOf(item.getQuantity()))
                                .concat(" шт.\n");
            boxItemRepository.save(boxItem);
        }
        box.setDescription(description);
        boxRepository.save(box);
        boxDto.setDescription(description);
        boxDto.setId(box.getId());
        return boxDto;
    }

    public void closeBox(Long id) {
        Optional<Box> optionalBox = boxRepository.findById(id);
        if (optionalBox.isPresent()) {
            Box box = optionalBox.get();
            box.setStatus(Status.CLOSED);
            boxRepository.save(box);
        } else {
            throw new EntityNotFoundException("Бокс с id " + id + " не найден");
        }
    }

    public List<BoxItemDto> getBoxItemDtoByBox(List<BoxItemDto> boxItemDtos, Long productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            BoxItemDto boxItemDto = new BoxItemDto();
            boxItemDto.setProductId(product.getId());
            boxItemDto.setProductName(product.getName());
            boxItemDto.setImageUrl(product.getImage());
            boxItemDto.setPrice(product.getPrice());
            boxItemDto.setQuantity(quantity);
            boxItemDtos.add(boxItemDto);
        } else {
            throw new EntityNotFoundException("Закуска с id " + productId + " не найдена");
        }
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
