package lk.ijse.gdse.springboot.back_end.service.impl;

import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfileCardDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.*;
import lk.ijse.gdse.springboot.back_end.repository.*;
import lk.ijse.gdse.springboot.back_end.service.UserPostService;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {

    private final JobPostRepository jobPostRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final ModelMapper modelMapper;
    private final ImagePath imagePath;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public List<JobPostDTO> getAllPost(Pageable pageable) {
        Page<JobPost> all = jobPostRepository.findAll(pageable);



        List<JobPostDTO> jobPostDTOS = all.getContent().stream()


                .map(post -> {
                    JobPostDTO dto = modelMapper.map(post, JobPostDTO.class);
                    dto.setUsername(post.getUser().getId());
                    dto.setJobImagePath(imagePath.getBase64FromFile(post.getJobImagePath()));
                    return dto;
                })
                .toList();


        return jobPostDTOS;
    }


    public ProfilePhotoNameDTO getProfilePitcherAndName(int userId) {
        System.out.println("run");
        CompanyProfile companyProfile  = companyProfileRepository.findProfileImagePathAndCompanyNameByUserId(userId);

        System.out.println(companyProfile);
        return new ProfilePhotoNameDTO(
                companyProfile.getCompanyName(),
                imagePath.getBase64FromFile(companyProfile.getProfileImagePath())
        );


    }

    public List<ProfileCardDTO> getAllProfiles(String userName) {
        // 1. User
        User user = userRepository.findUserByUsername(userName);
        if (user == null) return Collections.emptyList();

        // 2. UserProfile
        UserProfile userProfile = userProfileRepository.findAllByUser(user);
        if (userProfile == null) return Collections.emptyList();

        // 3. Companies followed by user
        List<Followers> followedList = followRepository.findAllByUser(userProfile);

        // 4. Make a set of followed company IDs
        Set<Long> followedCompanyIds = followedList.stream()
                .map(f -> f.getCompany().getId())
                .collect(Collectors.toSet());

        // 5. All company cards
        List<CompanyProfile> profileCards = companyProfileRepository.findAll();
//        List<ProfileCardDTO> profileCards = (List<ProfileCardDTO>) all;
        List<ProfileCardDTO> profileCardDTOS = new ArrayList<>();

        // 6. Filter: add only companies NOT followed by user
        for (CompanyProfile profileCard : profileCards) {
            ProfileCardDTO dto = new ProfileCardDTO();
            if (!followedCompanyIds.contains(profileCard.getId())) {
                dto.setId(profileCard.getId());
                dto.setProfileImagePath(imagePath.getBase64FromFile(profileCard.getProfileImagePath()));
                dto.setCompanyName(profileCard.getCompanyName());
                dto.setUserName(profileCard.getUser().getUsername());
                profileCardDTOS.add(dto);
            }
        }

        return profileCardDTOS;
    }

    public List<ProfileCardDTO> getFollowedCompanies(String userName) {
        // 1. Get User
        User user = userRepository.findUserByUsername(userName);
        if (user == null) return Collections.emptyList();

        // 2. Get UserProfile
        UserProfile userProfile = userProfileRepository.findAllByUser(user);
        if (userProfile == null) return Collections.emptyList();

        // 3. Get list of Followers (i.e., followed companies)
        List<Followers> followedList = followRepository.findAllByUser(userProfile);
        if (followedList.isEmpty()) return Collections.emptyList();

        // 4. Map to ProfileCardDTO
        List<ProfileCardDTO> profileCardDTOS = new ArrayList<>();
        for (Followers follower : followedList) {
            CompanyProfile company = follower.getCompany();
            ProfileCardDTO dto = new ProfileCardDTO();

            dto.setId(follower.getId());
            dto.setCompanyName(company.getCompanyName());
            dto.setProfileImagePath(imagePath.getBase64FromFile(company.getProfileImagePath()));
            profileCardDTOS.add(dto);
        }

        return profileCardDTOS;
    }


    public List<JobPostDTO> getJob(String query) {

        List<JobPost> byJobType = jobPostRepository.findByJobType(query);

        System.out.println(byJobType);


        return byJobType.stream()


                .map(post -> {
                    JobPostDTO dto = modelMapper.map(post, JobPostDTO.class);
                    dto.setUsername(post.getUser().getId());
                    dto.setJobImagePath(imagePath.getBase64FromFile(post.getJobImagePath()));
                    return dto;
                })
                .toList();
    }

    public List<String> getJobTitle(String query) {

        List<String> jobTitleByJobTitleLike = jobPostRepository.findJobTitleByJobTitleLike(query + "%");

        System.out.println(jobTitleByJobTitleLike);
        return jobTitleByJobTitleLike;
    }
}
