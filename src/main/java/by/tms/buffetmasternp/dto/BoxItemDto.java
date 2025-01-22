package by.tms.buffetmasternp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxItemDto {

    public Long productId;
    public String productName;
    public String imageUrl;
    public int quantity;
}
