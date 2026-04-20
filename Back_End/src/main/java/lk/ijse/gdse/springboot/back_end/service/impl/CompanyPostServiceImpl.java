package lk.ijse.gdse.springboot.back_end.service.impl;

import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.JobPostRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lk.ijse.gdse.springboot.back_end.service.CompanyPostService;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyPostServiceImpl implements CompanyPostService {

    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CompanyProfileRepository companyProfileRepository;
    private final ImagePath imagePath;

    public List<JobPostDTO> getAllPostByUserName(String name) {
        User userByUsername = userRepository.findUserByUsername(name);
        if (userByUsername == null) return null;

        System.out.println(userByUsername.getId());
//        return null;

        try {
            List<JobPost> byUserUsername = jobPostRepository.findByUser_Id(userByUsername.getId());
            List<JobPostDTO> posts = byUserUsername.stream()
                    .map(post -> {
                        JobPostDTO dto = modelMapper.map(post, JobPostDTO.class);
                        dto.setUsername(post.getUser().getId()); // extra field
                        dto.setJobImagePath(imagePath.getBase64FromFile(post.getJobImagePath()));
                        return dto;
                    })
                    .toList();
            return posts;
        }catch (Exception e){
            return null;
        }

    }

    public Object getProfilePitcherAndName(int userName) {
        System.out.println("run");
        CompanyProfile companyProfile  = companyProfileRepository.findProfileImagePathAndCompanyNameByUserId(userName);

        System.out.println(companyProfile);
        return new ProfilePhotoNameDTO(
            companyProfile.getCompanyName(),
            imagePath.getBase64FromFile(companyProfile.getProfileImagePath())
        );
    }

    public boolean deletePost(long postId) {

        try {
            jobPostRepository.deleteById( postId);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
