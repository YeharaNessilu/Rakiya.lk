package lk.ijse.gdse.springboot.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfileDetailsDTO {
    private String userName;
    private String  bannerImage;
    private String profileImage;
    private String profileName;
    private String title;
    private String address;
}
