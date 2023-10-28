package com.example.demo.projects.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "projects")
public class projects {

    @Id
    private String _id;
    private String userId;
    private String developer;
    private String imageSrcMain;
    private String selectedType;
    private String selectedYear;
    private String selectedMonth;
    private String selectedBathroom;
    private String selectedBedrooms;
    private String selectedProperty;
    private String selectedStatus;
    private String projectName;
    private String priceFrom;
    private String sizeFrom;
    private String selectedLocation;
    private String ownLocation;
    private String lng;
    private String lat;
    private String exterior;
    private String description;
    private String descriptionRu;

    private Map<String,List<PlanData>> plans;
    private List<String> architectureGallery;
    private List<String> interiorGallery;

    private List<infrastructureImages> infrastructures;
    private Map<String,paymentPlan> PaymentPlans;

    private boolean activeProject = true;

    public projects(String userId, String developer, String imageSrcMain, String selectedType, String selectedYear, String selectedMonth, String selectedBathroom, String selectedBedrooms, String selectedStatus, String projectName, String priceFrom, String sizeFrom, String selectedLocation, String ownLocation, String lng, String lat, String exterior, String description, Map<String, List<PlanData>> plans, List<String> architectureGallery, List<String> interiorGallery, List<infrastructureImages> infrastructures, Map<String, paymentPlan> paymentPlans) {
        this.userId = userId;
        this.developer = developer;
        this.imageSrcMain = imageSrcMain;
        this.selectedType = selectedType;
        this.selectedYear = selectedYear;
        this.selectedMonth = selectedMonth;
        this.selectedBathroom = selectedBathroom;
        this.selectedBedrooms = selectedBedrooms;
        this.selectedStatus = selectedStatus;
        this.projectName = projectName;
        this.priceFrom = priceFrom;
        this.sizeFrom = sizeFrom;
        this.selectedLocation = selectedLocation;
        this.ownLocation = ownLocation;
        this.lng = lng;
        this.lat = lat;
        this.exterior = exterior;
        this.description = description;
        this.plans = plans;
        this.architectureGallery = architectureGallery;
        this.interiorGallery = interiorGallery;
        this.infrastructures = infrastructures;
        PaymentPlans = paymentPlans;
    }


}
