package com.example.demo.projects.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class infrastructureImages {
    private String imgSrc;
    private String name;
    private String nameRu;
    private String desc;
    private String descRu;
}
