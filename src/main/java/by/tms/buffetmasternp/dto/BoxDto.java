package by.tms.buffetmasternp.dto;

import by.tms.buffetmasternp.entity.GroupBox;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Status status;
    private Type type;
    private List<BoxItemDto> boxItemDtos;
    private GroupBox groupBox;
    private Double price;
}
