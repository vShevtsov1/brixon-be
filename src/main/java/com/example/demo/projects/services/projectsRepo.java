package com.example.demo.projects.services;

import com.example.demo.projects.data.projects;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface projectsRepo extends MongoRepository<projects, String> {

    List<projects> findAllByUserId(String id);
    List<projects> findAllByActiveProjectIsTrue();
    List<projects> findAllByProjectNameStartingWithIgnoreCaseAndUserId(String startWith, String userId);

    @Query("{ 'selectedStatus': ?0, 'selectedProperty': ?1, '$expr': { '$and': [ { '$gte': [{ '$toInt': '$priceFrom' }, ?2] }, { '$lte': [{ '$toInt': '$priceFrom' }, ?3] }, { '$eq': ['$selectedBedrooms', ?4] }, { '$and': [ { '$gte': [{ '$toInt': '$sizeFrom' }, ?5] }, { '$lte': [{ '$toInt': '$sizeFrom' }, ?6] } ] } ] } }")
    List<projects> findProjectsByCriteria(String selectedStatus, String selectedProperty, int priceFromMin, int priceFromMax, String selectedBedrooms,  int sizeFromMin, int sizeFromMax);


}
