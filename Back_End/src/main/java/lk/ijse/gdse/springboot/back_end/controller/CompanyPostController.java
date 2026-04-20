package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.service.CompanyPostService;
import lk.ijse.gdse.springboot.back_end.service.impl.CompanyPostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin
@RequiredArgsConstructor
public class CompanyPostController {

    private final CompanyPostService companyPostService;
    private final ModelMapper modelMapper;

    @GetMapping("/getCompanyPost")
    public APIResponse getCompanyPost(Authentication authentication) {
        return new APIResponse(
                200,
                "get company all post",
                companyPostService.getAllPostByUserName(authentication.getName())


        );
    }

    @GetMapping("/profile")
    public APIResponse getProfile(@RequestParam int userID) {
        System.out.println("controller");
        return new APIResponse(
                200,
                "get Profile",
                companyPostService.getProfilePitcherAndName(userID)
        );
    }

    @DeleteMapping("/deletePost")
    public APIResponse deletePost(@RequestParam long postId) {
        return new APIResponse(
                200,
                "deleted",
                companyPostService.deletePost(postId)
        );
    }

}
