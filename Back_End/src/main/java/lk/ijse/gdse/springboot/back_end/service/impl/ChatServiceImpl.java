package lk.ijse.gdse.springboot.back_end.service.impl;

import lk.ijse.gdse.springboot.back_end.dto.ChatDTO;
import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;
import lk.ijse.gdse.springboot.back_end.dto.GetChatDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.*;
import lk.ijse.gdse.springboot.back_end.repository.*;
import lk.ijse.gdse.springboot.back_end.service.ChatService;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final JobPostRepository jobPostRepository;
    private final ModelMapper modelMapper;
    private final ImagePath imagePath;
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public CompanyProfileDTO findCompanyByJobId(int jobId) {
        JobPost bId = jobPostRepository.findById(jobId);
        CompanyProfile profile = companyProfileRepository.findCompanyProfileByUser(bId.getUser());
        profile.setProfileImagePath(imagePath.getBase64FromFile(profile.getProfileImagePath()));

        return modelMapper.map(profile, CompanyProfileDTO.class);


    }

    public ChatDTO getChatData(int jobId, String userId) {
        CompanyProfile companyProfile = companyProfileRepository.findById(jobId);

        UserProfile userProfile = userProfileRepository.findAllByUser(userRepository.findUserByUsername(userId));

        Chat chat = chatRepository.findByUserProfileAndCompanyProfile(userProfile, companyProfile);

//        return modelMapper.map(chat, ChatDTO.class);
        if (chat == null) {
            Chat newChat = new Chat();
                newChat.setCompanyProfile(companyProfile);
                newChat.setUserProfile(userProfile);
            Chat save = chatRepository.save(newChat);
            return modelMapper.map(save, ChatDTO.class);
        }

        return new ChatDTO(
                chat.getId(),
                chat.getUserProfile().getId(),
                chat.getUserProfile().getId(),
                chat.getMessage(),
                chat.getImage()
        );

    }

    public ChatDTO saveMessage(ChatDTO dto) {
        Chat chat = chatRepository.findById(dto.getId());
        if (chat == null) {
            chat.setMessage(dto.getMessage());
            chatRepository.save(chat);
        }
        String messages = chat.getMessage();
        messages+=dto.getMessage();
        chat.setMessage(messages);
        Chat save = chatRepository.save(chat);

        return modelMapper.map(save, ChatDTO.class);
    }

    public List<GetChatDTO> findChatByUserName(String username) {
        User user = userRepository.findUserByUsername(username);
        CompanyProfile companyProfile = companyProfileRepository.findCompanyProfileByUser(user);
        UserProfile userProfile = userProfileRepository.findAllByUser(user);

        if (companyProfile == null) {
            List<Chat> userChat = chatRepository.findByUserProfile(userProfile);
            if (userChat == null) return null;

            List<GetChatDTO> dtos = new ArrayList<>();
                for (Chat chat : userChat) {
                     GetChatDTO dto = new  GetChatDTO(
                            modelMapper.map(chat, ChatDTO.class),
                             imagePath.getBase64FromFile(chat.getCompanyProfile().getProfileImagePath()),
                            chat.getCompanyProfile().getCompanyName()
                    );
                     dtos.add(dto);
                }
                return dtos;
        }
        List<Chat> companyChat = chatRepository.findByCompanyProfile(companyProfile);
        if (companyChat == null) return null;


        List<GetChatDTO> dtos = new ArrayList<>();
        for (Chat chat : companyChat) {
            GetChatDTO dto = new  GetChatDTO(
                    modelMapper.map(chat, ChatDTO.class),
                    imagePath.getBase64FromFile(chat.getUserProfile().getProfileImage()),
                    chat.getUserProfile().getProfileName()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public ProfilePhotoNameDTO getProfile(String name) {
        User user = userRepository.findUserByUsername(name);

        if (user == null) return null;
        ProfilePhotoNameDTO dto = new  ProfilePhotoNameDTO();
        try {
            CompanyProfile companyProfile = companyProfileRepository.findCompanyProfileByUser(user);
            dto.setProfileImage(imagePath.getBase64FromFile(companyProfile.getProfileImagePath()));
            dto.setCompanyName(companyProfile.getCompanyName());

            return dto;
        }catch (Exception e){
            UserProfile userProfile = userProfileRepository.findAllByUser(user);

            dto.setProfileImage(imagePath.getBase64FromFile(userProfile.getProfileImage()));
            dto.setCompanyName(userProfile.getProfileName());
            return dto;
        }


    }
}
