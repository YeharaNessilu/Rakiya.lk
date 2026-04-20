package lk.ijse.gdse.springboot.back_end.controller;


import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.ChatDTO;
import lk.ijse.gdse.springboot.back_end.service.ChatService;
import lk.ijse.gdse.springboot.back_end.service.impl.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin
@RequiredArgsConstructor
public class CompanyChatController {
    private final ChatService chatService;

    @GetMapping("/searchChats")
    public APIResponse searchChats(@RequestParam String username){
        System.out.println(username);
        return new APIResponse(
                200,
                "send Company",
                chatService.findChatByUserName(username)
        );
    }

    @PostMapping("/saveMessages")
    public APIResponse saveMessages(@RequestBody ChatDTO chatDTO){
        System.out.println(chatDTO.getMessage());
        System.out.println(chatDTO.getId());
        return new APIResponse(
                200,
                "send",
                chatService.saveMessage(chatDTO)
        );
    }
    @GetMapping("/getChatProfile")
    public APIResponse getProfile(Authentication authentication){
        return new APIResponse(
                200,
                "Get Profile",
                chatService.getProfile(authentication.getName())
        );
    }
}
