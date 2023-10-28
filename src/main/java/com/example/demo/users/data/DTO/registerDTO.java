package com.example.demo.users.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class registerDTO {

    private String email;
    private String company;
    private String registrationCode;
    private String pinCode;
}
