package com.adarsh.projectmanagement_service.repo;

import com.adarsh.projectmanagement_service.dto.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Projectrepo extends JpaRepository<Project,Long> {
}
