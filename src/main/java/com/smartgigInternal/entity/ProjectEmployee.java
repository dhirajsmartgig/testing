package com.smartgigInternal.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="project_employee")
public class ProjectEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectEmployeeId;
	private Long projectId;
	private Long employeeDetailsId;
	private Long clientId;
	private Date onBoardDate;
	private Date offBoardDate;
	private boolean status;
	
}