package com.smartgigInternal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProjectRequest {

	private String projectName;
	private String fullName;
	private String clientName;
	private String role;
	private boolean status;

}
