package lk.ijse.gdse.springboot.back_end.service.impl;

import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lk.ijse.gdse.springboot.back_end.service.CompanyService;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

//import static jdk.internal.jrtfs.JrtFileAttributeView.AttrID.extension;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyProfileRepository companyProfileRepository;
    private final UserRepository userRepository;
    private final ImagePath imagePath;

    private final ModelMapper modelMapper;

    public String saveOrUpdate(CompanyProfileDTO companyProfileDTO) {
//        System.out.println("companyProfileDTO: " + companyProfileDTO);
        System.out.println("companyProfileDTO: " + companyProfileDTO);
        System.out.println(companyProfileDTO.getMali());

        User optionalUser = userRepository.findUserByUsername(companyProfileDTO.getMali());

        if (optionalUser == null) {
            System.out.println("userwa hoyaganna bari una");
            return "cannot find user";
        }


        String banner = imagePath.saveImage(companyProfileDTO.getBannerImage());
        String profile = imagePath.saveImage(companyProfileDTO.getProfileImage());

        if (banner == null) {
            System.out.println("image eka save une na (banner)");
            return "cannot save banner image";
        }
        if (profile == null) {
            System.out.println("image eka save une na (profile)");
            return "cannot save profile image";
        }

        try {
            CompanyProfile company = companyProfileRepository.findCompanyProfileByUser(optionalUser);

            if (company != null) {
                // -------- Update case --------
                company.setBannerImagePath(banner);
                company.setCompanyName(companyProfileDTO.getCompanyName());
                company.setIndustry(companyProfileDTO.getIndustry());
                company.setLocations(companyProfileDTO.getLocations());
                company.setMission(companyProfileDTO.getMission());
                company.setOverview(companyProfileDTO.getOverview());
                company.setProfileImagePath(profile);
                company.setTagline(companyProfileDTO.getTagline());
                company.setVision(companyProfileDTO.getVision());

                companyProfileRepository.save(company);
                return "company profile updated successfully";
            } else {
                // -------- Insert case --------
                CompanyProfile newCompany = new CompanyProfile();
                newCompany.setCompanyName(companyProfileDTO.getCompanyName());
                newCompany.setTagline(companyProfileDTO.getTagline());
                newCompany.setIndustry(companyProfileDTO.getIndustry());
                newCompany.setOverview(companyProfileDTO.getOverview());
                newCompany.setMission(companyProfileDTO.getMission());
                newCompany.setVision(companyProfileDTO.getVision());
                newCompany.setLocations(companyProfileDTO.getLocations());
                newCompany.setProfileImagePath(profile);
                newCompany.setBannerImagePath(banner);
                newCompany.setUser(optionalUser);

                companyProfileRepository.save(newCompany);
                return "company profile created successfully";
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            return "error while saving/updating company profile";
        }
    }



    public CompanyProfileDTO getAll(String userName) {
        User userId = userRepository.findUserByUsername(userName);


        if(userId == null) return null;
        System.out.println("user: " + userId.getId());
        System.out.println("userNmae" + userName);

        CompanyProfile companyProfile = companyProfileRepository.findCompanyProfileByUser(userId);
            companyProfile.setProfileImagePath(imagePath.getBase64FromFile(companyProfile.getProfileImagePath()));
            companyProfile.setBannerImagePath(imagePath.getBase64FromFile(companyProfile.getBannerImagePath()));
        System.out.println(companyProfile.getId());

        return modelMapper.map(companyProfile, CompanyProfileDTO.class);
//        return new CompanyProfileDTO(
//                imagePath.getBase64FromFile(companyProfile.getProfileImagePath()),
//                companyProfile.getCompanyName(),
//                imagePath.getBase64FromFile(companyProfile.getBannerImagePath()),
//                companyProfile.getTagline(),
//                companyProfile.getIndustry(),
//                companyProfile.getOverview(),
//                companyProfile.getMission(),
//                companyProfile.getVision(),
//                companyProfile.getLocations(),
//                companyProfile.getId().toString()
//        );

    }

}
