package com.smartgigInternal.service;

import java.util.List;
import java.util.Map;

import com.smartgigInternal.entity.LaptopInventory;

public interface LaptopInventoryService {

	public Map<String ,String > saveLaptop(LaptopInventory laptopInventory);

	public List<Map<String ,Object >> getAllAssignedLaptops(boolean isReturn);

	public Map<String ,Object > getAssignedLaptops(Long id);

	public Map<String, String> editAssignedLaptops(LaptopInventory laptopInventory);

	public List<Map<String, Object>> getAssignedLaptopsDetails(Long employeeDetailsId);

	public Object getAssignedLaptopsDetailsbyid(Long employeeDetailsId);

}
