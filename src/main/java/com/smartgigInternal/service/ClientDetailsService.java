package com.smartgigInternal.service;

import java.util.List;
import java.util.Map;

import com.smartgigInternal.dto.ClientDetailsDto;
import com.smartgigInternal.entity.ClientDetails;

public interface ClientDetailsService {

	public Map<String, Object> saveClient(ClientDetails client);

	public ClientDetails getClintDetails(Long clientId);

	public String changeStatusOfClient(Long clientId);

	public List<ClientDetails> getAllClientDetails();

	public Map<String, Object> editClientDetails(ClientDetailsDto dto, Long clientId);

	public Map<String, Object> getNoOfActiveProjectAndEmp(Long clientId);

}