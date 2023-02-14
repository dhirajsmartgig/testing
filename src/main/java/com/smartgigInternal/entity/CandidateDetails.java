package com.smartgigInternal.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Data
@Table(name = "candidates")
public class CandidateDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String candidateName;
	private String primarySkill;
	private String secondarySkill;
	private String mobileNo;
	private String email;
	private String alterNo;
	private Date dob;
	private String currentOrg;
	private Float totalExp;
	private String isOnNoticePeriod;
	private Integer noticePeriod;
	private String resume;
	private String linkedinLink;
	private String appliedFor;
	private String candidateHr;
	private String currentCtc;
	private String expectedCtc;
	private String status;
	

}
