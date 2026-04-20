package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.*;


import lk.ijse.gdse.springboot.back_end.service.CompanyService;
import lk.ijse.gdse.springboot.back_end.service.UserPostService;
import lk.ijse.gdse.springboot.back_end.service.UserProfileService;
import lk.ijse.gdse.springboot.back_end.service.impl.CompanyServiceImpl;
import lk.ijse.gdse.springboot.back_end.service.impl.UserPostServiceImpl;
import lk.ijse.gdse.springboot.back_end.service.impl.UserProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final UserPostService userPostService;
    private final CompanyService companyService;


    @PostMapping("/updateProfileDetails")
    public APIResponse updateUserProfileDetails(@RequestBody UserProfileDetailsDTO userProfileDetailsDTO){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.updateOrSveUserProfile(userProfileDetailsDTO)
        );

    }

    @PostMapping("/updateProfileAbout")
    public APIResponse updateUserProfileAbout(@RequestBody UserProfileAboutDTO userProfileAboutDTO){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.updateOrSveUserProfileAbout(userProfileAboutDTO)
        );

    }

    @PostMapping("/updateProfileExperience")
    public APIResponse updateUserProfileExperience(@RequestBody UserProfileExperienceDTO userProfileExperienceDTO){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.updateOrSveUserProfileExperience(userProfileExperienceDTO)
        );

    }

    @GetMapping("/getUserProfile")
    public APIResponse getUserProfile(Authentication authentication){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.getProfile(authentication.getName())
        );

    }

    @GetMapping("/getFollowingList")
    public APIResponse getFollowingList(Authentication authentication){
        return new APIResponse(
                200,
                "getFollowingList",
                userPostService.getFollowedCompanies(authentication.getName())
        );
    }

    @DeleteMapping("/unfollow")
    public APIResponse unFollow(@RequestParam long id){
        return new APIResponse(
          200,
          "unFollow",
          userProfileService.unfollow(id)
        );
    }

    @GetMapping("/notifications")
    private APIResponse getNotification( Authentication authentication) {
//        System.out.println("qqqqqqqqqqqq");
        return new APIResponse(
                200,
                "send notification",
                    userProfileService.getNotifications(authentication.getName())
                );
    }

    @GetMapping("/userProfilePhoto")
    private APIResponse getProfilePhoto(Authentication authentication){
        return new APIResponse(
                200,
                "get Profile Photo",
                userProfileService.getProfilePhoto(authentication.getName())
        );
    }

    @GetMapping("/userId")
    public APIResponse getUserId(Authentication authentication){
        return new APIResponse(
                200,
                "get UserId",
                userProfileService.getUserId(authentication.getName())
        );
    }

    @GetMapping("/getProfile")
    public APIResponse getProfile(@RequestParam String userName) {
//        System.out.println(authentication.getName());
        CompanyProfileDTO all = companyService.getAll(userName);
        return new APIResponse(
                200,
                "get all details",
                all
        );
    }

}
