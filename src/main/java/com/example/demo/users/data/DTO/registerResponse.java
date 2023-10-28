package com.example.demo.users.data.DTO;

import com.example.demo.users.data.help.registerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class registerResponse {

    private registerStatus registerStatus;
    private String message;

}
