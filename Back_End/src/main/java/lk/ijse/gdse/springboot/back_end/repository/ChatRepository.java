package lk.ijse.gdse.springboot.back_end.repository;

import lk.ijse.gdse.springboot.back_end.entity.Chat;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findByUserProfileAndCompanyProfile(UserProfile userProfile, CompanyProfile companyProfile);

    Chat findById(long id);

     List<Chat> findByUserProfile(UserProfile userProfile);

    List<Chat> findByCompanyProfile(CompanyProfile companyProfile);



}
