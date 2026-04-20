package lk.ijse.gdse.springboot.back_end.service.impl;

import lk.ijse.gdse.springboot.back_end.dto.*;
import lk.ijse.gdse.springboot.back_end.entity.OTP;
import lk.ijse.gdse.springboot.back_end.entity.Role;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.OTPRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lk.ijse.gdse.springboot.back_end.service.AuthService;
import lk.ijse.gdse.springboot.back_end.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Random random;
    private final SimpleMailMessage simpleMailMessage;
    private final JavaMailSenderImpl mailSender;
    private final OTPRepository otpRepository;

    public  AuthResponseDTO register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
            User user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(Role.valueOf(userDto.getRole()))
                    .build();
            userRepository.save(user);
            String token = jwtUtil.generateToken(userDto.getUsername(),userDto.getRole());
            return new AuthResponseDTO(token);
        }
        throw new RuntimeException("Username already exists");
    }

    public AuthResponseDTO authenticate(AuthDTO authDTO) {

        Optional<User> byUsername = Optional.ofNullable(userRepository.findByUsername(authDTO.getUsername()).orElseThrow(() -> new RuntimeException("USER name note found")));

        if (!passwordEncoder.matches(authDTO.getPassword(), byUsername.get().getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtUtil.generateToken(authDTO.getUsername(), String.valueOf(byUsername.get().getRole()));
        return new AuthResponseDTO(token);

    }

    public String sendOTP(OtpDTO otpDTO){

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("yeharanessilu123@gmail.com");
        mailSender.setPassword("bfko cuxh wvbf wmlu"); // Gmail App Password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");


        System.out.println(otpDTO.getEmail());
        simpleMailMessage.setTo(otpDTO.getEmail());
        simpleMailMessage.setSubject("Your OTP Code");
        SecureRandom random = new SecureRandom();
        String otp = String.valueOf(1000 + random.nextInt(9000));
        simpleMailMessage.setText("Your OTP is: " +otp+"\nThis code will expire in 1 minutes.");

        try {
            mailSender.send(simpleMailMessage);

            otpRepository.save(new OTP(otpDTO.getEmail(),otp, LocalDateTime.now()));

            return "OTP Send Success";

        } catch (RuntimeException e) {
            return "Can't send OTP" ;
        }

    }

    public boolean validateOTP(ValidateOTPDTO validateOTPDTO){
        try {
            OTP otp = otpRepository.findByEmail(validateOTPDTO.getEmail());

            if (otp.getOtp().equals(validateOTPDTO.getOtp())){

                Duration duration = Duration.between(otp.getTime(), validateOTPDTO.getTime());
                if (duration.toMinutes() <= 1){
                    System.out.println("otp eka hari");
                    return true;

                }
                System.out.println("otp eka hari time out");

                return false;
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        }

    }

}
