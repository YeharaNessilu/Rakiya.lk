package lk.ijse.gdse.springboot.back_end.service.impl;

import lk.ijse.gdse.springboot.back_end.dto.FollowersDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.Followers;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.entity.UserProfile;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.FollowRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lk.ijse.gdse.springboot.back_end.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final UserProfileRepository userProfileRepository;

    public boolean addFollower(FollowersDTO followersDTO) {
        User user = userRepository.findUserByUsername(followersDTO.getUserName());

        CompanyProfile byId = companyProfileRepository.findById(followersDTO.getCompany_id());
        if (byId == null) return false;

        UserProfile userProfile = userProfileRepository.findAllByUser(user);


        try {
            Followers followers = new Followers();
            followers.setUser(userProfile);
            followers.setCompany(byId);
            followRepository.save(followers);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
