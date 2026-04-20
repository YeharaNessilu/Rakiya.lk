package lk.ijse.gdse.springboot.back_end.service;


import lk.ijse.gdse.springboot.back_end.dto.FollowersDTO;

public interface FollowService {

    /**
     * Add a follower for a company.
     * @param followersDTO contains username of user and company id
     * @return true if added successfully, false otherwise
     */
    boolean addFollower(FollowersDTO followersDTO);
}
