package com.example.demo.projects.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createProjectDTO {
    private MultipartFile imageSrc;
    private MultipartFile imageSrcMain;
    private String selectedType;
    private String selectedYear;
    private String selectedMonth;
    private String selectedBathroom;
    private String selectedBedrooms;
    private String selectedStatus;
    private String projectName;
    private String priceFrom;
    private String sizeFrom;
    private String selectedLocation;
    private String ownLocation;
    private String selectedProperty;
    private String lng;
    private String lat;
    private MultipartFile exterior;
    private String description;
    private String descriptionRu;
    private List<PlansData> plansStudio;
    private List<PlansData> plans1;
    private List<PlansData> plans2;
    private List<PlansData> plans3;
    private List<PlansData> plans4;
    private List<MultipartFile> architecture;
    private List<MultipartFile> interior;
    private List<Infrastructure> infrastructures;
    private List<paymentsPlan> blocks;
}
