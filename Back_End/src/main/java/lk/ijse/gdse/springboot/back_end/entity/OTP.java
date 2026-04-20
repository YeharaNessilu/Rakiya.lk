package lk.ijse.gdse.springboot.back_end.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
    @Id
    private String email;
    private String otp;
    private LocalDateTime time;
}
