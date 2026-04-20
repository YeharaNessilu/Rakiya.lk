package lk.ijse.gdse.springboot.back_end.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private String profileName;
    private String bannerImage;
    private String profileImage;
    private String title;
    private String address;
    private String about;
    private String education;
    private String contact;
    private String experience;
    private String skills;
    private String userId;

}
