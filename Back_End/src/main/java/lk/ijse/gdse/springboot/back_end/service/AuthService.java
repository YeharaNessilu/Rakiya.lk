package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.*;

public interface AuthService {

    AuthResponseDTO register(UserDto userDto);

    AuthResponseDTO authenticate(AuthDTO authDTO);

    String sendOTP(OtpDTO otpDTO);

    boolean validateOTP(ValidateOTPDTO validateOTPDTO);
}
