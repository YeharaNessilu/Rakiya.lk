package lk.ijse.gdse.springboot.back_end.repository;

import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    List<JobPost> findByUser_Id(int username);
    JobPost findById(int jobPostId);

    @Query("SELECT j FROM JobPost j WHERE j.jobTitle = :jobType")
    List<JobPost> findByJobType(String jobType);

    @Query("SELECT j.jobTitle FROM JobPost j WHERE j.jobTitle LIKE :jobTitle")
    List<String> findJobTitleByJobTitleLike(String jobTitle);

}
