package lk.ijse.gdse.springboot.back_end.service;


import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfileCardDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserPostService {

    List<JobPostDTO> getAllPost(Pageable pageable);

    ProfilePhotoNameDTO getProfilePitcherAndName(int userId);

    List<ProfileCardDTO> getAllProfiles(String userName);

    List<ProfileCardDTO> getFollowedCompanies(String userName);

    List<JobPostDTO> getJob(String query);

    List<String> getJobTitle(String query);
}
