package lk.ijse.gdse.springboot.back_end.config;

import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;

import java.util.List;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> new
                        org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()))


                        )).orElseThrow(
                                () -> new UsernameNotFoundException("user not found")
                );
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Random random(){
        return new Random();
    }
    @Bean
    public SimpleMailMessage mailMessage(){
        return new SimpleMailMessage();
    }

    @Bean
    public JavaMailSenderImpl mailSender(){
        return new JavaMailSenderImpl();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
