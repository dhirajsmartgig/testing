package com.smartgigInternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgigInternal.entity.ClientDetails;

public interface ClinetDetailsRepository extends JpaRepository<ClientDetails, Long> {

	ClientDetails findByClientId(Long ClientId);

	List<ClientDetails> findByStatus(boolean status);

	ClientDetails findByClientNameAndStatus(String clientName, boolean status);

	ClientDetails findByClientName(String clientName);

	ClientDetails findByClientNameAndStatusAndLocation(String clientName, boolean b, String location);
}