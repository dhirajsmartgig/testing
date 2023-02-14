package com.smartgigInternal.serviceImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartgigInternal.dto.ClientDetailsDto;
import com.smartgigInternal.entity.ClientDetails;
import com.smartgigInternal.entity.ProjectDetails;
import com.smartgigInternal.entity.ProjectEmployee;
import com.smartgigInternal.repository.ClinetDetailsRepository;
import com.smartgigInternal.repository.EmployeeProjectRepository;
import com.smartgigInternal.repository.ProjectDetailsRepository;
import com.smartgigInternal.service.ClientDetailsService;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

	@Autowired
	private ClinetDetailsRepository clientDetailsRepository;
	@Autowired
	private ProjectDetailsRepository projectDetailsRepository;
	@Autowired
	private EmployeeProjectRepository employeeProjectRepository;

	@Override
	public Map<String, Object> saveClient(ClientDetails client) {
		Map<String, Object> map = new HashMap();
		ClientDetails cl = clientDetailsRepository.findByClientNameAndStatus(client.getClientName(), true);
		if (cl == null) {
			ClientDetails clientDetails = this.clientDetailsRepository.save(client);
			if (clientDetails != null) {
				map.put("msg", "client added");
				map.put("clientId", clientDetails.getClientId());
			}
		} else {
			map.put("msg", "this client already present");
		}
		return map;
	}

	@Override
	public ClientDetails getClintDetails(Long clientId) {
		ClientDetails clientDetails = clientDetailsRepository.findByClientId(clientId);
		return clientDetails;
	}

	@Override
	public String changeStatusOfClient(Long clientId) {
		ClientDetails clientDetails = clientDetailsRepository.findByClientId(clientId);
		List<ProjectDetails> project = this.projectDetailsRepository.findByClientId(clientDetails.getClientId());
		List<ProjectEmployee> assignedPro = employeeProjectRepository
				.findByClientIdAndStatus(clientDetails.getClientId(), true);
		for (ProjectDetails pro : project) {
			pro.setStatus("Completed");
			this.projectDetailsRepository.save(pro);
		}
		for (ProjectEmployee assigned : assignedPro) {
			assigned.setStatus(false);
			this.employeeProjectRepository.save(assigned);
		}
		clientDetails.setStatus(false);
		this.clientDetailsRepository.save(clientDetails);
		return "deleted";
	}

	@Override
	public List<ClientDetails> getAllClientDetails() {
		return clientDetailsRepository.findByStatus(true);
	}

	@Override
	public Map<String, Object> editClientDetails(ClientDetailsDto dto, Long clientId) {
		Map<String, Object> map = new HashMap();
		ClientDetails cl=null;
		ClientDetails exsistingClientDetails = clientDetailsRepository.findByClientId(clientId);
		if (exsistingClientDetails != null) {
			exsistingClientDetails.setClientName(dto.getClientName());
			exsistingClientDetails.setLocation(dto.getLocation());
			exsistingClientDetails.setGst(dto.getGst());
			exsistingClientDetails.setAddress(dto.getAddress());
			exsistingClientDetails.setContactNumber(dto.getContactNumber());
			exsistingClientDetails.setEmailId(dto.getEmailId());
			exsistingClientDetails.setSpocName(dto.getSpocName());
			cl = clientDetailsRepository.save(exsistingClientDetails);
		}
		if (cl != null) {
			map.put("msg", "client details updated");
		}
		return map;
	}

	@Override
	public Map<String, Object> getNoOfActiveProjectAndEmp(Long clientId) {
		ClientDetails clientDetails = clientDetailsRepository.findByClientId(clientId);
		List<ProjectDetails> project = this.projectDetailsRepository
				.findByClientIdAndStatus(clientDetails.getClientId(), "InProgress");
		List<ProjectEmployee> assignedPro = employeeProjectRepository
				.findByClientIdAndStatus(clientDetails.getClientId(), true);
		Map<String, Object> map = new HashMap();
		map.put("NoOfActiveProject", project.size());
		map.put("NoOfActiveEmp", assignedPro.size());
		return map;
	}

}