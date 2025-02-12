package com.adarsh.projectmanagementsystem.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adarsh.projectmanagementsystem.Dto.ProjectDetails;
import com.adarsh.projectmanagementsystem.Repo.ProjectDetailsRepo;

@Repository
public class ProjectDetailsDao {

    @Autowired
    private ProjectDetailsRepo detailsRepo;

    public ProjectDetails insert(ProjectDetails details)
    {
        return detailsRepo.save(details);
    }

    public List<ProjectDetails> getAllDetails()
    {
        return detailsRepo.findAll();
    }

    public Optional<ProjectDetails> getById(int i)
    {
        return detailsRepo.findById(i);
    }

    public String Update(ProjectDetails details)
    {
        Optional<ProjectDetails> optional=detailsRepo.findById(details.getId());
        
        if(optional.isPresent())
        {
            detailsRepo.save(details);
            System.out.println("hello");
            return "Updated";

        }
        else{
            System.out.println("hello");
            return "Project Details Not Found";
        }
    }

    public String delete(int i)
    {
        Optional<ProjectDetails> optional=detailsRepo.findById(i);

        if(optional!=null)
        {
            detailsRepo.deleteById(i);
            return "Deleted Successfully";
        }
        else{
            return "Details not found";
        }
    }
}
