package com.example.demo.projects.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlansData {

    private MultipartFile imgSrc;
    private String price;
    private String size;
}
