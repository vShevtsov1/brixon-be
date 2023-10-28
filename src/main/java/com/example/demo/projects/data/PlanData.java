package com.example.demo.projects.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanData {
    private String imgSrc;
    private String price;
    private String size;
}
