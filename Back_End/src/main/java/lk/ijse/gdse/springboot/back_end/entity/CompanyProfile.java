package lk.ijse.gdse.springboot.back_end.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String tagline;
    private String industry;
    private String followersCount;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(columnDefinition = "TEXT")
    private String mission;

    @Column(columnDefinition = "TEXT")
    private String vision;

    @Column(columnDefinition = "TEXT")
    private String locations;

    private String profileImagePath;
    private String bannerImagePath;

        // One-to-One Relationship with User

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    @JsonBackReference
    private User user;


    // Relationship with follow table
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Followers> followers = new ArrayList<>();


    @OneToMany(mappedBy = "companyProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats ;

}

