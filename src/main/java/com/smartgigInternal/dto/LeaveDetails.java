package com.smartgigInternal.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "leave_details")
public class LeaveDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "reason")
	private String leaveSubject;
	private Date fromDate;
	private Date toDate;
	private double totalDays;
	private String leaveType;
	@Column(name = "employee_details_id")
	private Long employeeDetailsId;

}
