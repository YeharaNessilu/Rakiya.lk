package lk.ijse.gdse.springboot.back_end.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCardDTO {
    private long id;
    private String companyName;
    private String profileImagePath;
    private String followersCount;
    private String userName;
}
