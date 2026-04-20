package lk.ijse.gdse.springboot.back_end.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String profileName;
    private String bannerImage;
    private String profileImage;
    private String title;
    private String address;
    @Column(length = 2000)
    private String about;
    @Column(length = 2000)
    private String education;
    @Column(length = 2000)
    private String contact;
    @Column(length = 2000)
    private String experience;
    @Column(length = 2000)
    private String skills;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    @JsonBackReference
    private User user;


    // Relationship with follow table
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Followers> follows = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats ;
}
