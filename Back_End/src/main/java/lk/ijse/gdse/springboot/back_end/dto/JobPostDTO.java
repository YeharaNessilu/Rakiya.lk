package lk.ijse.gdse.springboot.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostDTO {
    private Long id;
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
    // user ekata one nam username witharak yawanna
    private int username;
}
