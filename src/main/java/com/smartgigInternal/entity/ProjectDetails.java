
package com.smartgigInternal.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class ProjectDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;
	private String projectName;
	private Long clientId;
	private String projectDescription;
	private String reportingManager;
	private String clientEmail;
	private String status;
	private Date startDate;
	private Date endDate;

}
