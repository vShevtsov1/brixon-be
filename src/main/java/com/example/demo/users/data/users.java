package com.example.demo.users.data;


import com.example.demo.users.data.help.userRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class users {

    @Id
    private String _id;

    @Field("nickname")
    private String nickName;

    @Field("email")
    private String email;

    @Field("company")
    private String company;

    @Field("pincode")
    private String pinCode;

    @Field("registrationCode")
    private String registrationCode;

    @Field("role")
    private userRole userRole;
    @Field("profileImage")
    private String image;

    @Field
    private Boolean userRegister = false;

}
