package com.smartgigInternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgigInternal.entity.LaptopInventory;

public interface LaptopInventoryRepository extends JpaRepository<LaptopInventory, Long>{

	List<LaptopInventory> findByReturnStatus(boolean isReturn);
	
	LaptopInventory findByEmployeeDetailsId(Long employeeDetailsId);

	LaptopInventory findByLaptopIdAndReturnStatus(Long id, boolean b);

	LaptopInventory findByEmployeeDetailsIdAndReturnStatus(Long id, boolean b);
}
