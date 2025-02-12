package com.adarsh.projectmanagementsystem.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adarsh.projectmanagementsystem.Dto.ProjectDetails;
import com.adarsh.projectmanagementsystem.Service.ProjectDetailsService;

@RestController
@RequestMapping("/projectdetails")
public class ProjectDetailsController {

    @Autowired
    private ProjectDetailsService detailsService;

    @PostMapping("/insert")
    public ProjectDetails insert(@RequestBody ProjectDetails details)
    {
        return detailsService.insert(details);
    }

     @GetMapping("/getalldetails")    
      public List<ProjectDetails> getAllDetails()
    {
        return detailsService.getAllDetails();
    }

    @GetMapping("/getbyid/{i}")
    public Optional<ProjectDetails> getById(@PathVariable int i)
    {
        return detailsService.getById(i);
    }

    @PutMapping("/updatedetails")
    public String Update(@RequestBody ProjectDetails details)
    {
        return detailsService.Update(details);
    }

    @DeleteMapping("/deletedetails/{i}")
    public String delete(@PathVariable int i)
    {
        return detailsService.delete(i);
    }
}
