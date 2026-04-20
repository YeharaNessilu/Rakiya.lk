package lk.ijse.gdse.springboot.back_end.service.impl;

import lk.ijse.gdse.springboot.back_end.controller.NotificationController;
import lk.ijse.gdse.springboot.back_end.dto.CreateJobDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import lk.ijse.gdse.springboot.back_end.entity.Notification;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.*;
import lk.ijse.gdse.springboot.back_end.service.CreateJobService;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateJobServiceImpl implements CreateJobService {

    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;
    private final ImagePath imagePath;
    private final FollowRepository followRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationController notificationController;
    private final CompanyProfileRepository companyProfileRepository;
    private final ModelMapper modelMapper;

    public String postJob(CreateJobDTO createJobDTO) {



        User user = userRepository.findUserByUsername(createJobDTO.getUserName());
        if (user == null) return "cant find user";
        CompanyProfile company = companyProfileRepository.findCompanyProfileByUser(user);

        String savedImagePath = imagePath.saveImage(createJobDTO.getJobImagePath());
        if (savedImagePath == null) return "cant save image";
        createJobDTO.setJobImagePath(savedImagePath);


        try {
//            System.out.println(createJobDTO);
//            JobPost jobPost = modelMapper.map(createJobDTO, JobPost.class);
            JobPost jobPostSaved = new JobPost();

            jobPostSaved.setJobTitle(createJobDTO.getJobTitle());
            jobPostSaved.setAddress(createJobDTO.getAddress());
            jobPostSaved.setLocation(createJobDTO.getLocation());
            jobPostSaved.setExperienceRequired(createJobDTO.getExperienceRequired());
            jobPostSaved.setSalaryRange(createJobDTO.getSalaryRange());
            jobPostSaved.setJobType(createJobDTO.getJobType());
            jobPostSaved.setWorkMode(createJobDTO.getWorkMode());
            jobPostSaved.setSkills(createJobDTO.getSkills());
            jobPostSaved.setJobDescription(createJobDTO.getJobDescription());
            jobPostSaved.setJobImagePath(savedImagePath);
            jobPostSaved.setCreatedAt(createJobDTO.getCreatedAt());
            jobPostSaved.setUser(user);
            JobPost save = jobPostRepository.save(jobPostSaved);
//            jobPostRepository.save(new JobPost(
//                    createJobDTO.getJobTitle(),
//                    createJobDTO.getAddress(),
//                    createJobDTO.getLocation(),
//                    createJobDTO.getExperienceRequired(),
//                    createJobDTO.getSalaryRange(),
//                    createJobDTO.getJobType(),
//                    createJobDTO.getWorkMode(),
//                    createJobDTO.getSkills(),
//                    createJobDTO.getJobDescription(),
//                    createJobDTO.getJobImagePath(),
//                    createJobDTO.getCreatedAt(),
//                    byUsername.get()
//            ));
            notification(company,  save);
             return "job save success";
        } catch (Exception e) {
            return "cant save job";
        }
    }
    private void notification(CompanyProfile companyProfile, JobPost jobPost) {
        System.out.println("Service eka run una");

        // Find all followers of the company
        followRepository.findAllByCompany(companyProfile).forEach(follow -> {
            User follower = follow.getUser().getUser(); // follower user
            Notification notification = new Notification();
            notification.setUser(follower);
            notification.setMessage("New post from " + companyProfile.getCompanyName());
            notification.setJobPost(jobPost);
//            notification.setImage(companyProfile.getProfileImagePath());

            try {
                Notification saved = notificationRepository.save(notification);

                // Send only to this follower if emitter exists
                notificationController.sendNotification(saved);

            } catch (Exception e) {
                System.out.println("notification save une na for follower: " + follower.getId());
            }
        });
    }




}
