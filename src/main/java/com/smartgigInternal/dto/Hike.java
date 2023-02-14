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
@Table(name = "hike")
@Data
public class Hike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long employeeDetailsId;
	private Date effectiveDate;
	private Double hikeAmount;
	private Double hikeVariablePay;
	@Column(name = "hike_total_ctc")
	private Double total_ctc;
	private Date updatedOn;

}
