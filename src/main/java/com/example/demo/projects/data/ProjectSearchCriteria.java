package com.example.demo.projects.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSearchCriteria {
    private String selectedStatus;
    private String selectedProperty;
    private String selectedBedrooms;
    private String priceFromMin;
    private String priceFromMax;
    private String sizeFromMin;
    private String sizeFromMax;

   
}
