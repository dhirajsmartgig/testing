package com.smartgigInternal.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.smartgigInternal.entity.Laptop;

public interface LaptopService {

	Map<String, String> saveLaptop(Laptop laptop);
	List<Map<String,Object>> getAllLaptop(boolean status);
	Laptop getLaptop(Long id);
	Map<String, String> updateLaptopDetails(Laptop laptop);
	String changeStatus(Long id);
	List<Map<String,Object>> laptopStock();
	List<Map<String, String>> saveBulkLaptopDetails(MultipartFile bulkUploadExcel);
	List<Map<String,Object>> getAssignedLaptop();

}