package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.CreateJobDTO;

import lk.ijse.gdse.springboot.back_end.service.CreateJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin
@RequiredArgsConstructor
public class CreateJobController {

    private final CreateJobService createJobService;

    @PostMapping("/postJob")
    public APIResponse postJob (@RequestBody CreateJobDTO createJobDTO) {
//        System.out.println(createJobDTO);
        return new APIResponse(
            200,
            "send Post",
                createJobService.postJob(createJobDTO)

        );
    }


}
