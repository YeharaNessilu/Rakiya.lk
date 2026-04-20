package lk.ijse.gdse.springboot.back_end.service;


import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;

public interface CompanyService {

    /**
     * Save or update a company profile.
     * @param companyProfileDTO DTO containing company profile data
     * @return Success or failure message
     */
    String saveOrUpdate(CompanyProfileDTO companyProfileDTO);

    /**
     * Get the company profile by username.
     * @param userName User's username
     * @return CompanyProfileDTO if exists, else null
     */
    CompanyProfileDTO getAll(String userName);
}
