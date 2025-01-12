package by.tms.buffetmasternp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
