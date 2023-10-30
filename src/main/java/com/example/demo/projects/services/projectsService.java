package com.example.demo.projects.services;

import com.example.demo.aws.S3Service;
import com.example.demo.projects.data.*;
import com.example.demo.users.services.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class projectsService {

    @Autowired
    private projectsRepo projectsRepo;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private userRepo userRepo;
    public void createProject(createProjectDTO createProjectDTO,String email) throws IOException {
        String uuid = UUID.randomUUID().toString();
        projects projects = new projects();
        projects.setUserId(userRepo.findByEmail(email).get_id());
        projects.setImageSrc(s3Service.uploadPhoto(uuid,createProjectDTO.getImageSrc()));
        projects.setImageSrcMain(s3Service.uploadPhoto(uuid,createProjectDTO.getImageSrcMain()));
        projects.setProjectName(createProjectDTO.getProjectName());
        projects.setSelectedType(createProjectDTO.getSelectedType());
        projects.setSelectedYear(createProjectDTO.getSelectedYear());
        projects.setSelectedProperty(createProjectDTO.getSelectedProperty());
        projects.setSelectedMonth(createProjectDTO.getSelectedMonth());
        projects.setSelectedBathroom(createProjectDTO.getSelectedBathroom());
        projects.setSelectedBedrooms(createProjectDTO.getSelectedBedrooms());
        projects.setSelectedStatus(createProjectDTO.getSelectedStatus());
        projects.setPriceFrom(createProjectDTO.getPriceFrom());
        projects.setSizeFrom(createProjectDTO.getSizeFrom());
        projects.setSelectedLocation(createProjectDTO.getSelectedLocation());
        projects.setOwnLocation(createProjectDTO.getOwnLocation());
        projects.setLng(createProjectDTO.getLng());
        projects.setLat(createProjectDTO.getLat());
        projects.setDescription(createProjectDTO.getDescription());
        projects.setDescriptionRu(createProjectDTO.getDescriptionRu());
        projects.setExterior(s3Service.uploadPhoto(uuid,createProjectDTO.getExterior()));
        Map<String, List<PlanData>> plans = new HashMap<>();
        plans.put("Studio", createPlanData(createProjectDTO.getPlansStudio(), uuid));
        plans.put("1", createPlanData(createProjectDTO.getPlans1(), uuid));
        plans.put("2", createPlanData(createProjectDTO.getPlans2(), uuid));
        plans.put("3", createPlanData(createProjectDTO.getPlans3(), uuid));
        plans.put("4+", createPlanData(createProjectDTO.getPlans4(), uuid));
        projects.setPlans(plans);
        List<String> architectureGallery = new ArrayList<>();
        for (MultipartFile file :createProjectDTO.getArchitecture()) {
            architectureGallery.add(s3Service.uploadPhoto(uuid,file));
        }
        List<String> interiorGallery = new ArrayList<>();
        for (MultipartFile file :createProjectDTO.getInterior()) {
            interiorGallery.add(s3Service.uploadPhoto(uuid,file));
        }
        projects.setArchitectureGallery(architectureGallery);
        projects.setInteriorGallery(interiorGallery);
         List<infrastructureImages> infrastructures = new ArrayList<>();
        for (Infrastructure data:createProjectDTO.getInfrastructures()) {
            infrastructures.add(new infrastructureImages(s3Service.uploadPhoto(uuid,data.getImgSrc()), data.getName(), data.getNameRu(),data.getDesc(),data.getDescRu()));
        }
        projects.setInfrastructures(infrastructures);
        Map<String,paymentPlan> PaymentPlans = new HashMap<>();
        for (paymentsPlan payments:createProjectDTO.getBlocks()) {
            PaymentPlans.put(payments.getBlockName(),new paymentPlan(payments.getPercent1(), payments.getSum1(),payments.getPercent2(),payments.getSum2(),payments.getPercent3(),payments.getSum3(), payments.getPercent4(), payments.getSum4()));
        }
        projects.setPaymentPlans(PaymentPlans);
        projectsRepo.save(projects);
    }
    List<PlanData> createPlanData(List<PlansData> plansData, String uuid) throws IOException {
        List<PlanData> planData = new ArrayList<>();
        if (plansData != null) {
            for (PlansData data : plansData) {
                planData.add(new PlanData(s3Service.uploadPhoto(uuid, data.getImgSrc()), data.getPrice(), data.getSize()));
            }
        }
        return planData;
    }

    public List<projects> findAllProjects(){
        return projectsRepo.findAllByActiveProjectIsTrue();
    }

    public projects getById(String id){
        return projectsRepo.findById(id).get();
    }

    public List<projects> getAllUserProjects(String email,String start){
        return projectsRepo.findAllByProjectNameStartingWithIgnoreCaseAndUserId(start,userRepo.findByEmail(email).get_id());
    }
    public void deleteProject(String id){
        projects projects = projectsRepo.findById(id).get();
        projectsRepo.delete(projects);
    }
    public void activateProject(String id){
        projects projects = projectsRepo.findById(id).get();
        if(projects.isActiveProject()){
            projects.setActiveProject(false);
        }
        else {
            projects.setActiveProject(true);
        }
        projectsRepo.save(projects);
    }
    public List<projects> findProjectsByCriteria(ProjectSearchCriteria searchCriteria) {
        int priceMin = Integer.parseInt(searchCriteria.getPriceFromMin());
        int priceMax = Integer.parseInt(searchCriteria.getPriceFromMax());
        int sizeMin = Integer.parseInt(searchCriteria.getSizeFromMin());
        int sizeMax = Integer.parseInt(searchCriteria.getSizeFromMax());


        return projectsRepo.findProjectsByCriteria(
                searchCriteria.getSelectedStatus(),
                searchCriteria.getSelectedProperty(),priceMin,priceMax,searchCriteria.getSelectedBedrooms(),  sizeMin,sizeMax
        );
    }

}
