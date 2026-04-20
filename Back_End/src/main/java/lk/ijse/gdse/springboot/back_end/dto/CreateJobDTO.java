package lk.ijse.gdse.springboot.back_end.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateJobDTO {

    private String jobTitle;
    private String address;
    private String location;
    private String experienceRequired;
    private String salaryRange;
    private String jobType;
    private String workMode;
    private String skills;
    private String jobDescription;
    private String jobImagePath;
    private LocalDateTime createdAt;
    private String userName;// foreign key
}
