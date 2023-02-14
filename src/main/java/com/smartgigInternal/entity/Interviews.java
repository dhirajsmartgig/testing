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
@Table(name = "interviews")
public class Interviews {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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