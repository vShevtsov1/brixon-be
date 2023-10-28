package com.example.demo.users.data.DTO;

import com.example.demo.users.data.help.loginStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class resetPasswordResponse {

    private loginStatus loginStatus;
}
