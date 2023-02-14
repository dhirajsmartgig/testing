 package com.smartgigInternal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="laptop_details")
public class Laptop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String srNo;
	private String company;
	private String model;
	private String processor;
	private String modelProductId;
	private String mtm;
	private String chargerId;
	private String powercableId;
	private String backPack;
	private String laptopType;
	private String mailId;
	private String lapPassword;	
	private String comment;
	private boolean status;
	private String ram;
	private Double amountRent;
	
}
