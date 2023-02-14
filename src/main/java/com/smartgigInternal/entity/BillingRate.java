package com.smartgigInternal.entity;

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
@Table(name = "billing_rate")
public class BillingRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "employee_details_id")
	private Long employeeDetailsId;

	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "project_id")
	private Long projectId;

	@Column(name = "from_date")
	private Date fromDate;

	@Column(name = "to_date")
	private Date toDate;

	@Column(name = "rate_type")
	private String rateType;
	private Long rate;

}
