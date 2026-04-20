package lk.ijse.gdse.springboot.back_end.service;


import lk.ijse.gdse.springboot.back_end.dto.*;

import java.util.List;

public interface UserProfileService {

    String updateOrSveUserProfile(UserProfileDetailsDTO userProfileDetailsDTO);

    String updateOrSveUserProfileAbout(UserProfileAboutDTO dto);

    String updateOrSveUserProfileExperience(UserProfileExperienceDTO dto);

    UserProfileDTO getProfile(String username);

    boolean unfollow(long id);

    List<NotificationDTO> getNotifications(String userName);

    String getProfilePhoto(String name);

    int getUserId(String name);
}
