package com.smartgigInternal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EmployeeLeaves {

	@Id
	private int id;
	private Long employeeDetailsId;
	private double casualLeaveAvailable;
	private double casualLeaveAvailed;
	private double sickLeaveAvailable;
	private double sickLeaveAvailed;
	private double totalPaidLeavesAvailable;
	private double totalPaidLeavesAvailed;
	
	@Column(name = "loss_of_pay_leaves")
	private double lOP;
	
}
