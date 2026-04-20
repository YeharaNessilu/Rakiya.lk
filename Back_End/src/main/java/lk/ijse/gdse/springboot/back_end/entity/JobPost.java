package lk.ijse.gdse.springboot.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String address;
    private String location;
    private String experienceRequired;
    private String salaryRange;
    private String jobType;
    private String workMode;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String jobDescription;

    private String jobImagePath;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "userId") // foreign key
    private User user;

    // ✅ This must match the field name in Notification
    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();


}
