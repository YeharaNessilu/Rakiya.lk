package lk.ijse.gdse.springboot.back_end.service;


import lk.ijse.gdse.springboot.back_end.dto.ChatDTO;
import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;
import lk.ijse.gdse.springboot.back_end.dto.GetChatDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;

import java.util.List;

public interface ChatService {

    CompanyProfileDTO findCompanyByJobId(int jobId);

    ChatDTO getChatData(int jobId, String userId);

    ChatDTO saveMessage(ChatDTO dto);

    List<GetChatDTO> findChatByUserName(String username);

    ProfilePhotoNameDTO getProfile(String name);
}
