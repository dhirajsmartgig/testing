package com.smartgigInternal.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InterviewDetailsDto {
    
    private Long candidateId;
    private String interviewType;
    private Date interviewDate;
    private double fromtime;
    private double totime;
    private String amAndpm;
    private String interviewer;
    private String interviewRound;
    private String roundStatus;
    private String comments;
    private String interviewStatus;

}