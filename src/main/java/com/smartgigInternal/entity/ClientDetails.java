package com.smartgigInternal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class ClientDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clientId;
	private String clientName;
	private String location;
	private boolean status;
	private String gst;
	private String address; 
	private String spocName;
	private String emailId;
	private String contactNumber;
	
	

}
