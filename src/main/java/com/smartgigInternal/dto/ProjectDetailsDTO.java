package com.smartgigInternal.dto;
import java.util.Date;

import lombok.Data;

@Data
public class ProjectDetailsDTO {
	private String projectName;
	private String clientName;
	private String projectDescription;
	private String reportingManager;
	private String clientEmail;
	private String status;
	private Date startDate;
	private Date endDate;
}