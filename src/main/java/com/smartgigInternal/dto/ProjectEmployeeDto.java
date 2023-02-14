package com.smartgigInternal.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProjectEmployeeDto {
	
	private List<String> employeeNameList;
	private String projectName;
	private Date onBoardDate;
	private Date offBoardDate;
	private boolean status;

}
