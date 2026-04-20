package lk.ijse.gdse.springboot.back_end.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChatDTO {
   private ChatDTO chatDTO;
    private String ProfileImage;
    private String ProfileName;
}
