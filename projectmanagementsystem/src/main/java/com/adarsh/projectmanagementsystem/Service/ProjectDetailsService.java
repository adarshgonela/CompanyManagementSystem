package com.adarsh.projectmanagementsystem.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.projectmanagementsystem.Dao.ProjectDetailsDao;
import com.adarsh.projectmanagementsystem.Dto.ProjectDetails;

@Service
public class ProjectDetailsService {
 
    @Autowired
    private ProjectDetailsDao detailsDao;

    public ProjectDetails insert(ProjectDetails projectDetails)
    {
        return detailsDao.insert(projectDetails);
    }

     public List<ProjectDetails> getAllDetails()
    {
        return detailsDao.getAllDetails();
    }

    public Optional<ProjectDetails> getById(int i)
    {
        return detailsDao.getById(i);
    }

    public String Update(ProjectDetails details)
    {
        return detailsDao.Update(details);
    }

    public String delete(int i)
    {
        return detailsDao.delete(i);
    }
}
