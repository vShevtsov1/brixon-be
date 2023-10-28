package com.example.demo.users.services;

import com.example.demo.aws.S3Service;
import com.example.demo.mail.EmailController;
import com.example.demo.security.TokenServices;
import com.example.demo.users.data.DTO.*;
import com.example.demo.users.data.help.loginStatus;
import com.example.demo.users.data.help.registerStatus;
import com.example.demo.users.data.help.userRole;
import com.example.demo.users.data.users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class userService {

    @Autowired
    private userRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenServices tokenServices;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private EmailController emailController;

    public users createNewPermission() {
        String uniqueCode = generateUniqueCode();
        users users = new users();
        users.setRegistrationCode(uniqueCode);

        return userRepo.save(users);
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (codeExists(code));

        return code;
    }

    private String generateRandomCode() {
        Random random = new Random();
        int codeInt = random.nextInt(900000) + 100000;
        return String.valueOf(codeInt);
    }
    private boolean codeExists(String code) {
        return userRepo.existsByRegistrationCode(code);
    }


    public registerResponse registerUser(registerDTO registerDTO){
        registerResponse registerResponse = new registerResponse();
        if(codeExists(registerDTO.getRegistrationCode())){
            users users = userRepo.findByRegistrationCode(registerDTO.getRegistrationCode());
            if(users.getUserRegister()){
                registerResponse.setRegisterStatus(registerStatus.USER_EXIST);
                registerResponse.setMessage("User Already Exists");
                return registerResponse;
            }
            else {
                users.setUserRegister(true);
                users.setUserRole(userRole.USER);
                users.setEmail(registerDTO.getEmail());
                users.setCompany(registerDTO.getCompany());
                users.setPinCode(passwordEncoder.encode(registerDTO.getPinCode()));
                users.setNickName(registerDTO.getEmail());
                userRepo.save(users);
                registerResponse.setRegisterStatus(registerStatus.OK);
                return registerResponse;
            }
        }
        else {
            registerResponse.setRegisterStatus(registerStatus.CODE_MISMATCH);
            registerResponse.setMessage("Wrong Code");

            return registerResponse;
        }
    }

    public loginResponse loginUser(loginDTO loginDTO){
        users users = userRepo.findByEmail(loginDTO.getEmail());
        if(users == null){
            return new loginResponse("", loginStatus.FAILED,null);
        }
        else {
            if(passwordEncoder.matches(loginDTO.getPinCode(),users.getPinCode())){
                return new loginResponse(tokenServices.generateTokenUser(users),loginStatus.OK,modelMapper.map(users, userDTO.class));
            }
            else {
                return new loginResponse("", loginStatus.FAILED,null);
            }
        }

    }
    public userDTO updateUserInfo(updateUserDTO userDTO,String email) throws IOException {
        users users =userRepo.findByEmail(email);
        if(userDTO.getImage() ==null){
            users.setNickName(userDTO.getNickname());
            userRepo.save(users);
        }
        else {
            String url = s3Service.uploadPhoto("profile_images",userDTO.getImage());
            users.setImage(url);
            users.setNickName(userDTO.getNickname());
            userRepo.save(users);
        }
        return modelMapper.map(users,com.example.demo.users.data.DTO.userDTO.class);
    }

    public userRegisterCheckResponse checkUserData(userRegisterCheckDTO userRegisterCheckDTO){
        userRegisterCheckResponse userRegisterCheckResponse = new userRegisterCheckResponse(loginStatus.OK,null);
        List<String> messages = new ArrayList<>();
        if(userRepo.existsByEmail(userRegisterCheckDTO.getEmail())){
            userRegisterCheckResponse.setCheckingStatus(loginStatus.FAILED);
            messages.add("User with this email already exists");
        }
        if(!userRepo.existsByRegistrationCode(userRegisterCheckDTO.getCode())){
            userRegisterCheckResponse.setCheckingStatus(loginStatus.FAILED);
            messages.add("Could not find this code, contact the manager to receive a valid code");
        }
        userRegisterCheckResponse.setMessages(messages);
        return userRegisterCheckResponse;
    }

    public resetPasswordResponse requestResetPassword(String email){
        users users = userRepo.findByEmail(email);
        if(users == null){
            return new resetPasswordResponse(loginStatus.FAILED);
        }
        emailController.sendEmail(users);
        return new resetPasswordResponse(loginStatus.OK);
    }

    public loginStatus resetPassword(String newPassword,String token){
        String mail  = tokenServices.getMail(token);
        users users =userRepo.findByEmail(mail);
        if(users == null){
            return loginStatus.FAILED;
        }
        else {
            users.setPinCode(passwordEncoder.encode(newPassword));
            return loginStatus.OK;
        }
    }
}
