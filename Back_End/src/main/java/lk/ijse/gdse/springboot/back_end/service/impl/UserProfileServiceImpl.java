package lk.ijse.gdse.springboot.back_end.service.impl;

import lk.ijse.gdse.springboot.back_end.dto.*;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.Notification;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.entity.UserProfile;
import lk.ijse.gdse.springboot.back_end.repository.*;
import lk.ijse.gdse.springboot.back_end.service.UserProfileService;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final ImagePath imagePath;
    private final FollowRepository followRepository;
    private final NotificationRepository notificationRepository;
    private final CompanyProfileRepository companyProfileRepository;

    public String updateOrSveUserProfile (UserProfileDetailsDTO userProfileDetailsDTO) {
        if (userProfileDetailsDTO == null) {
            return "Invalid request";
        }
        User byUsername = userRepository.findUserByUsername(userProfileDetailsDTO.getUserName());
       if (byUsername == null) return "Can't find user";


        UserProfile allByUser = userProfileRepository.findAllByUser(byUsername);

        if (allByUser == null) {
            try {
                UserProfile map = new UserProfile();
                map.setProfileName(userProfileDetailsDTO.getProfileName());
                map.setProfileImage(imagePath.saveImage(userProfileDetailsDTO.getProfileImage()));
                map.setAddress(userProfileDetailsDTO.getAddress());
                map.setTitle(userProfileDetailsDTO.getTitle());
                map.setBannerImage(imagePath.saveImage(userProfileDetailsDTO.getBannerImage()));
                map.setUser(byUsername); // important: set the user relation
                userProfileRepository.save(map);
                return "User profile saved successfully";
            }catch (Exception e) {
                return "Can't Save user profile";
            }
        }
        try {


// Common fields (always update)
            allByUser.setProfileName(userProfileDetailsDTO.getProfileName());
            allByUser.setAddress(userProfileDetailsDTO.getAddress());
            allByUser.setTitle(userProfileDetailsDTO.getTitle());

// Profile image (update only if not null/empty)
            if (userProfileDetailsDTO.getProfileImage() != null && !userProfileDetailsDTO.getProfileImage().isEmpty()) {
                allByUser.setProfileImage(imagePath.saveImage(userProfileDetailsDTO.getProfileImage()));
            }

// Banner image (update only if not null/empty)
            if (userProfileDetailsDTO.getBannerImage() != null && !userProfileDetailsDTO.getBannerImage().isEmpty()) {
                allByUser.setBannerImage(imagePath.saveImage(userProfileDetailsDTO.getBannerImage()));
            }

            userProfileRepository.save(allByUser);
            return "User profile updated successfully";

        }catch (Exception e) {
            return "Can't update user profile";
        }
    }

    public String updateOrSveUserProfileAbout(UserProfileAboutDTO dto) {
        User byUsername = userRepository.findUserByUsername(dto.getUserName());
        if (byUsername == null) return "user";


        UserProfile allByUser = userProfileRepository.findAllByUser(byUsername);
        System.out.println(allByUser);
        if (allByUser == null) {
            try {
                UserProfile map = new UserProfile();
                map.setAbout(dto.getAbout());
                map.setEducation(dto.getEducation());
                map.setContact(dto.getContact());
                map.setUser(byUsername); // important: set the user relation
                userProfileRepository.save(map);
                return "saved successfully";
            }catch (Exception e) {
                return "Can't Save";
            }
        }
        try {
            allByUser.setAbout(dto.getAbout());
            allByUser.setEducation(dto.getEducation());
            allByUser.setContact(dto.getContact());
            userProfileRepository.save(allByUser);

            return "updated successfully";
        }catch (Exception e) {
            return "Can't update ";
        }
    }

    public String updateOrSveUserProfileExperience(UserProfileExperienceDTO dto) {
        User byUsername = userRepository.findUserByUsername(dto.getUserName());
        if (byUsername == null) return "user";


        UserProfile allByUser = userProfileRepository.findAllByUser(byUsername);
        System.out.println(allByUser);
        if (allByUser == null) {
            try {
                UserProfile map = new UserProfile();
                map.setExperience(dto.getExperience());
                map.setUser(byUsername);
                 // important: set the user relation
                userProfileRepository.save(map);
                return "saved successfully";
            }catch (Exception e) {
                return "Can't Save";
            }
        }
        try {
            allByUser.setExperience(dto.getExperience());
            userProfileRepository.save(allByUser);

            return "updated successfully";
        }catch (Exception e) {
            return "Can't update ";
        }
    }


    public UserProfileDTO getProfile(String username) {
        User user = userRepository.findUserByUsername(username);

        UserProfile userProfile = userProfileRepository.findAllByUser(user);

        userProfile.setProfileImage(imagePath.getBase64FromFile(userProfile.getProfileImage()));
        userProfile.setBannerImage(imagePath.getBase64FromFile(userProfile.getBannerImage()));

        return modelMapper.map(userProfile, UserProfileDTO.class);

    }
    public boolean unfollow(long id){
        try {
            followRepository.deleteById( id);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public List<NotificationDTO> getNotifications(String userName) {
        User user = userRepository.findUserByUsername(userName);

        List<Notification> notifications = notificationRepository.findAllByUserOrderByIdDesc(user);

//        CompanyProfile profile = companyProfileRepository.findCompanyProfileByUser();
        return notifications.stream()
                .map(notification -> {
                    CompanyProfile profile = companyProfileRepository.findCompanyProfileByUser(notification.getJobPost().getUser());
                    System.out.println(notification.getJobPost().getUser().getCompanyProfile().getCompanyName());
                    NotificationDTO dto = new NotificationDTO();
                    dto.setMessage(notification.getMessage());
                    dto.setCompanyName(notification.getJobPost().getUser().getCompanyProfile().getCompanyName());
                    dto.setProfileImage(imagePath.getBase64FromFile(profile.getProfileImagePath()));
                    return dto;
                })
                .toList();
    }

    public String getProfilePhoto(String name) {
        User user = userRepository.findUserByUsername(name);
        if (user == null) return null;
        UserProfile allByUser = userProfileRepository.findAllByUser(user);
        if (allByUser == null) return null;
        return imagePath.getBase64FromFile(allByUser.getProfileImage());

    }

    public int getUserId(String name) {
        User user = userRepository.findUserByUsername(name);
        if (user == null) return -1;
        return user.getId();
    }
}
