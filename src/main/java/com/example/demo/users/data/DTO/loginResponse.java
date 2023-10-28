package com.example.demo.users.data.DTO;

import com.example.demo.users.data.help.loginStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginResponse {
    private String jwt;
    private loginStatus loginStatus;
    private userDTO userDTO;
}
