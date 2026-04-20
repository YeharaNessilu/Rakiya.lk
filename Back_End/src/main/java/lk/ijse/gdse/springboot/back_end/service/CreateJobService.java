package lk.ijse.gdse.springboot.back_end.service;


import lk.ijse.gdse.springboot.back_end.dto.CreateJobDTO;

public interface CreateJobService {

    /**
     * Create a new job post and notify followers.
     * @param createJobDTO Job post data
     * @return Success or failure message
     */
    String postJob(CreateJobDTO createJobDTO);
}
