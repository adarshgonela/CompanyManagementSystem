package com.adarsh.projectmanagementsystem.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adarsh.projectmanagementsystem.Dto.ProjectDetails;

public interface ProjectDetailsRepo  extends JpaRepository<ProjectDetails,Integer>{

}
