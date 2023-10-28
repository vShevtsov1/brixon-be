package com.example.demo.projects;

import com.example.demo.projects.data.ProjectSearchCriteria;
import com.example.demo.projects.data.createProjectDTO;
import com.example.demo.projects.data.projects;
import com.example.demo.projects.services.projectsService;
import com.example.demo.users.services.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class projectController {

    @Autowired
    private projectsService projectsService;
    @Autowired
    private userRepo userRepo;

    @PostMapping("/create-project")
    public void createNewProject(@ModelAttribute createProjectDTO createProjectDTO, Authentication authentication) throws IOException {
       projectsService.createProject(createProjectDTO,authentication.getPrincipal().toString());
    }

    @GetMapping("/get-all")
    public List<projects> getAllProjects()  {
       return projectsService.findAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<projects> getProjectById(@PathVariable String id) {
        try {
            projects project = projectsService.getById(id);
            if (project != null) {
                return new ResponseEntity<>(project, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/my-projects")
    public ResponseEntity<List<projects>> getAllMyProjects(@RequestParam String start, Authentication authentication) {
        return ResponseEntity.ok(projectsService.getAllUserProjects(authentication.getPrincipal().toString(),start));
    }

    @PostMapping("/delete-project")
    public void deleteProject(@RequestParam String projectId){
        projectsService.deleteProject(projectId);
    }

    @PostMapping("/active-project")
    public void activateProject(@RequestParam String projectId){
        projectsService.activateProject(projectId);
    }

    @PostMapping("/search")
    public List<projects> searchProjects(@RequestBody ProjectSearchCriteria searchCriteria) {
        return projectsService.findProjectsByCriteria(searchCriteria);
    }
}
