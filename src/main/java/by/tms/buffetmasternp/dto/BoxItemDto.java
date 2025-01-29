package by.tms.buffetmasternp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxItemDto {

    private Long productId;
    private String productName;
    private String productDescription;
    private String imageUrl;
    private Double price;
    private int min;
    private int quantity;
}
