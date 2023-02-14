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
@Table(name = "employee_billing")
public class EmployeeBilling {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long employeeDetailsId;
	private Long clientId;
	private Long projectId;
	private Date fromDate;
	private Date toDate;
	private Long day;
	private Long noOfLeaves;
	private Long totalDays;
	private Long dayRate;
	private Long totalAmount;
}
