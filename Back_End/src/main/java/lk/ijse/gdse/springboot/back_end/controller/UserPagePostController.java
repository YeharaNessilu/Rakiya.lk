package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.FollowersDTO;
import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;

import lk.ijse.gdse.springboot.back_end.service.FollowService;
import lk.ijse.gdse.springboot.back_end.service.UserPostService;
import lk.ijse.gdse.springboot.back_end.service.impl.FollowServiceImpl;
import lk.ijse.gdse.springboot.back_end.service.impl.UserPostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;


@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserPagePostController {

    private final UserPostService userPostService;
    private final FollowService followService;

    @GetMapping("/allPost")
    public APIResponse allPost( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<JobPostDTO> posts = userPostService.getAllPost(pageable);

        return new APIResponse(
                200,
                "Send All posts",
                posts
        );
    }

    @GetMapping("/postProfile")
    public APIResponse postProfile( @RequestParam int userId) {
        System.out.println(userId);
         ProfilePhotoNameDTO profilePitcherAndName = userPostService.getProfilePitcherAndName(userId);

        return new APIResponse(
                200,
                "get post profile image",
                profilePitcherAndName
        );

    }


    @GetMapping("/getAllProfile")
    public APIResponse getAllProfile(Authentication authentication) {
        return new APIResponse(
                200,
                "get all profiles",
                userPostService.getAllProfiles(authentication.getName())
        );
    }

    @PostMapping("/addFollowers")
    public APIResponse addFollowers(@RequestBody FollowersDTO followersDTO) {
        boolean b = followService.addFollower(followersDTO);
        return new APIResponse(
                200,
                "Follow",
                b
        );
    }

    @GetMapping("/searchJob")
    public APIResponse search(@RequestParam String query) {
        return new APIResponse(
                200,
                "search",
                userPostService.getJobTitle(query)
        );
    }

    @GetMapping("/getSearchJob")
    public APIResponse getSearchedJob(@RequestParam String query){
        System.out.println(query);
        return new APIResponse(
                200,
                "search",
                userPostService.getJob(query)
        );
    }



}
