package com.smartgigInternal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBillingRequest {

	private String fullName;
	private String projectName;
	private String clientName;

}
