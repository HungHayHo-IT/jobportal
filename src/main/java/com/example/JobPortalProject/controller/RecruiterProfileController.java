package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.RecruiterProfile;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.repository.UsersRepository;
import com.example.JobPortalProject.services.RecruiterProfileService;
import com.example.JobPortalProject.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {
    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentName = authentication.getName();
            Users users = usersRepository.findByEmail(currentName).orElseThrow(
                    ()->new UsernameNotFoundException("could not found user")
            );

            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());

            if(!recruiterProfile.isEmpty()){
                model.addAttribute("profile",recruiterProfile.get());
            }
        }
        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile multipartFile, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(
                    ()-> new UsernameNotFoundException("could not found user")
            );
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile",recruiterProfile);

        String fileName = ""; // luu ten file anh dai dien
        if(!multipartFile.getOriginalFilename().equals("")){//multipartFile: Đối tượng MultipartFile chứa file được upload từ form
        //getOriginalFilename(): Lấy tên gốc của file từ client
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            //Objects.requireNonNull(...): Đảm bảo tên file không phải null
            //StringUtils.cleanPath(...): Làm sạch đường dẫn file
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
        String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId(); //tạo đường dẫn thư mục lưu file ảnh dựa trên user ID

        try {
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/dashboard/";
    }

}
