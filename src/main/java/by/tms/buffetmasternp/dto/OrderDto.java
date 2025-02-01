package by.tms.buffetmasternp.dto;

import by.tms.buffetmasternp.entity.Box;
import by.tms.buffetmasternp.enums.StatusOrder;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {

    private Long id;
    private LocalDate date;
    private String address;
    private String phone;
    private Double price;
    private String comment;
    private StatusOrder status;
    private List<Box> boxes;
}
