package by.tms.buffetmasternp.dto;

import by.tms.buffetmasternp.enums.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private String date;
    private Long accountId;
    private String address;
    private String phone;
    private Double price;
    private String comment;
    private StatusOrder status;
    private List<BoxDto> boxes;
}
