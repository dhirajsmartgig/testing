package com.smartgigInternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgigInternal.entity.ProjectDetails;

public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, Long> {

	ProjectDetails findByProjectId(Long projectId);

	ProjectDetails findByProjectName(String projectName);

	List<ProjectDetails> findByClientIdAndStatus(Long clientId, String string);

	List<ProjectDetails> findByClientId(Long clientId);

	List<ProjectDetails> findByStatus(String status);

	ProjectDetails findByProjectNameAndClientId(String projectName, Long clientId);

}
