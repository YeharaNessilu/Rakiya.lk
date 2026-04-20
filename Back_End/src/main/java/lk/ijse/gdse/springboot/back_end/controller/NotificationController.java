package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.NotificationDTO;
import lk.ijse.gdse.springboot.back_end.entity.Notification;
import lk.ijse.gdse.springboot.back_end.repository.NotificationRepository;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/notification")
@CrossOrigin
@RequiredArgsConstructor
public class NotificationController {


    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final ImagePath imagePath;

    @GetMapping("/stream/{userId}")
    public SseEmitter streamNotifications(@PathVariable Long userId) {
        System.out.println(userId);
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter); // ✅ Save emitter
        System.out.println("Emitter saved for userId: " + userId); // print කරලා confirm කරන්න
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError(e -> emitters.remove(userId));
        return emitter;
    }


    public void sendNotification(Notification notification) {
        Long userId = (long) notification.getUser().getId();
        System.out.println(userId);
        SseEmitter emitter = emitters.get(userId);
        System.out.println(emitter);
        if (emitter != null) {
            try {
                assert notification.getJobPost() != null;
                NotificationDTO dto = new NotificationDTO(
                        notification.getJobPost().getUser().getCompanyProfile().getCompanyName(),
                                notification.getMessage(),
                                imagePath.getBase64FromFile( notification.getImage())

                );
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(dto));
            } catch (Exception e) {
                emitters.remove(userId);
            }
        }
    }



    // Optional: fetch existing unread notifications
//    @GetMapping
//    public List<Notification> getUnread(@AuthenticationPrincipal User user) {
//        return notificationRepository.findByUserAndReadFalse(user);
//    }

}
