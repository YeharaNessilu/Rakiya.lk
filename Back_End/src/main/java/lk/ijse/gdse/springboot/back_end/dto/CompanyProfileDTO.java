package lk.ijse.gdse.springboot.back_end.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyProfileDTO {

    private int id;
    private String profileImage;
    private String companyName;
    private String bannerImage;
    private String tagline;
    private String industry;
    private String overview;
    private String mission;
    private String vision;
    private String locations;
//
    private String mali;
}
