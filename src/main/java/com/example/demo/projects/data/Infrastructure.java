package com.example.demo.projects.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Infrastructure {

    private MultipartFile imgSrc;
    private String name;
    private String nameRu;
    private String desc;
    private String descRu;
}
