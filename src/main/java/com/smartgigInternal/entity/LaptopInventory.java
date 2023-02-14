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
@Table(name = "assigned_laptop")
public class LaptopInventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long employeeDetailsId;
	private Long laptopId ;
	private Date issueDate ;
	private Date returnDate;
	private boolean returnStatus;
	private Date replaceDate;

}
