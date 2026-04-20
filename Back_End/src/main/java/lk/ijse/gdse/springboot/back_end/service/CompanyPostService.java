package lk.ijse.gdse.springboot.back_end.service;


import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;

import java.util.List;

public interface CompanyPostService {

    List<JobPostDTO> getAllPostByUserName(String name);

    Object getProfilePitcherAndName(int userId);

    boolean deletePost(long postId);
}
