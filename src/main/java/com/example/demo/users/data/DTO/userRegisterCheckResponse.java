package com.example.demo.users.data.DTO;

import com.example.demo.users.data.help.loginStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userRegisterCheckResponse {

    private loginStatus checkingStatus;
    private List<String> messages;
}
