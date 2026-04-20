package lk.ijse.gdse.springboot.back_end.controller;


import lk.ijse.gdse.springboot.back_end.dto.*;

import lk.ijse.gdse.springboot.back_end.service.AuthService;
import lk.ijse.gdse.springboot.back_end.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(@RequestBody UserDto userDto) {
        System.out.println("ssss");
         return ResponseEntity.ok(
                 new APIResponse(
                         200,
                         "USER Register Successfully",
                         authService.register(userDto)
                 )
         );
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody AuthDTO authDTO) {
        System.out.println(authDTO.getUsername());
        return ResponseEntity.ok(new APIResponse(200, "OK", authService.authenticate(authDTO)));
    }

    @PostMapping("/otp")
    public ResponseEntity<APIResponse> registerOTP(@RequestBody OtpDTO otpDTO) {
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "OTP Send Successfully",
                        authService.sendOTP(otpDTO)
                )
        );

    }

    @PostMapping("/otpValidate")
    public boolean registerOTPValidate(@RequestBody ValidateOTPDTO validateOTPDTO) {

        validateOTPDTO.setTime(LocalDateTime.now());
        System.out.println("   ");
        System.out.println("   ");
        System.out.println("   ");
        System.out.println("   ");
        System.out.println("   ");
        System.out.println("   ");
        System.out.println(validateOTPDTO);

//        return ResponseEntity.ok(
//                new APIResponse(
//                        200,
//                        "OTP Validate Successfully",
//                        authService.validateOTP(validateOTPDTO)
//                )
//        );

        return authService.validateOTP(validateOTPDTO);
    }


}
