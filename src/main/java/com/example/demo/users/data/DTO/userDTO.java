package com.example.demo.users.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userDTO {

    private String _id;


    private String nickName;


    private String email;


    private String company;

    @Field("role")
    private com.example.demo.users.data.help.userRole userRole;
    @Field("profileImage")
    private String image;

    @Field
    private Boolean userRegister = false;
}
