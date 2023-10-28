package com.example.demo.users;

import com.example.demo.users.data.DTO.*;
import com.example.demo.users.data.help.loginStatus;
import com.example.demo.users.data.users;
import com.example.demo.users.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class usersController {

    @Autowired
    private userService userService;

    @PostMapping("/create-user")
    public ResponseEntity<users> createPermission() {
        users createdUsers = userService.createNewPermission();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }

    @PostMapping("/register-user")
    public ResponseEntity<registerResponse> registerUser(@RequestBody registerDTO registerDTO){
        return ResponseEntity.ok(userService.registerUser(registerDTO));
    }

    @PostMapping("/login-user")
    public ResponseEntity<loginResponse> registerUser(@RequestBody loginDTO loginDTO){
        return ResponseEntity.ok(userService.loginUser(loginDTO));
    }
    @PostMapping("/update-data")
    public ResponseEntity<userDTO> updateUserInfo(@ModelAttribute updateUserDTO updateUserDTO, Authentication authentication) throws IOException {
        return ResponseEntity.ok(userService.updateUserInfo(updateUserDTO,authentication.getPrincipal().toString()));
    }
    @PostMapping("/check-register")
    public ResponseEntity<userRegisterCheckResponse> checkUserRegister(@RequestBody userRegisterCheckDTO userRegisterCheckDTO){
        return ResponseEntity.ok(userService.checkUserData(userRegisterCheckDTO));
    }

    @PostMapping("/request-reset")
    public ResponseEntity<resetPasswordResponse> requestResetPassword(@RequestParam String email){

        try {
            return ResponseEntity.ok(userService.requestResetPassword(email));
        } catch (Exception e) {
           return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<loginStatus> resetPassword(@RequestParam String newPassword, @RequestParam String token){

        try {
            return ResponseEntity.ok(userService.resetPassword(newPassword, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
