package com.smartgigInternal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "documents")
public class DocumentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long documentationId;
	private Long employeeDetailsId;
	private String certificate10th;
	private String certificate12th;
	private String graduation;
	private String postGraduation;
	private String paySlip;
	private String offerLetter1;
	private String offerLetter2;
	private String offerLetter3;
	private String employeeImage;  
	private String resume;

}
