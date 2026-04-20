package lk.ijse.gdse.springboot.back_end.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfileExperienceDTO {
    private String userName;
    private String experience;

}
