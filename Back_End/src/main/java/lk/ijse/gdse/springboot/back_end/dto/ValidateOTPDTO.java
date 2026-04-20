package lk.ijse.gdse.springboot.back_end.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValidateOTPDTO {
    private String email;
    private String otp;
    private LocalDateTime time;
}
