package com.smartgigInternal.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String subject;
	private String message;
	private Date createdOn;
	private Date triggerDate;
	private boolean status;
	private String attachment;
	private String createdBy;

}
