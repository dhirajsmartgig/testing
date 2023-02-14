package com.smartgigInternal.dto;

import java.util.Date;

import lombok.Data;
@Data
public class CandidateDetailsDTO {
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
		private String currentCtc;
		private String expectedCtc;

}
