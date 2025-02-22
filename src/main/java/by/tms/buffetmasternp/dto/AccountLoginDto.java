package by.tms.buffetmasternp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginDto {

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;
}
