package lk.ijse.gdse.springboot.back_end.repository;

import lk.ijse.gdse.springboot.back_end.dto.ProfileCardDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {

   CompanyProfile findCompanyProfileByUser(User user);

   @Query("SELECT c FROM CompanyProfile c WHERE c.user.id = :id")
   CompanyProfile findProfileImagePathAndCompanyNameByUserId(int id);

   CompanyProfile findById(long id);

//   @Query("SELECT new lk.ijse.gdse.springboot.back_end.dto.ProfileCardDTO(c.id, c.companyName, c.profileImagePath, c.followersCount, c.com) FROM CompanyProfile c")
//   List<ProfileCardDTO> findProfileCard();



}
