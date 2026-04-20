package lk.ijse.gdse.springboot.back_end.controller;


import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;

import lk.ijse.gdse.springboot.back_end.service.CompanyService;
import lk.ijse.gdse.springboot.back_end.service.impl.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin
@RequiredArgsConstructor
public class CompanyMainPageController {

    private final CompanyService companyService;

    @GetMapping("getProfile")
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
