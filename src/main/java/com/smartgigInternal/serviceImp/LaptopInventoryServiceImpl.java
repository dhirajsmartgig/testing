package com.smartgigInternal.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.entity.Laptop;
import com.smartgigInternal.entity.LaptopInventory;
import com.smartgigInternal.repository.EmployeeDetailsRepository;
import com.smartgigInternal.repository.LaptopInventoryRepository;
import com.smartgigInternal.repository.LaptopRepository;
import com.smartgigInternal.service.LaptopInventoryService;

@Service
public class LaptopInventoryServiceImpl implements LaptopInventoryService {

	@Autowired
	private LaptopInventoryRepository laptopInventoryRepository;

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private LaptopRepository laptopRepository;

	@Override
	public Map<String, String> saveLaptop(LaptopInventory laptopInventory) {
		Map<String, String> map = new HashMap();
//		LaptopInventory laptop = laptopInventoryRepository
//				.findByEmployeeDetailsIdAndReturnStatus(laptopInventory.getEmployeeDetailsId(), true);
		LaptopInventory statusFalse = laptopInventoryRepository
				.findByEmployeeDetailsIdAndReturnStatus(laptopInventory.getEmployeeDetailsId(), false);

		if (statusFalse != null) {
			map.put("msg", "already assigned");
			return map;
		} else {
			laptopInventory.setReturnStatus(false);
			laptopInventoryRepository.save(laptopInventory);
			map.put("msg", "assigned");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getAllAssignedLaptops(boolean isReturn) {
		List<Map<String, Object>> list = new ArrayList();
		List<LaptopInventory> laptopList = laptopInventoryRepository.findByReturnStatus(isReturn);
		for (LaptopInventory laptop : laptopList) {
			Map<String, Object> map = new HashMap();
			EmployeeDetails emp = employeeDetailsRepository.findByEmployeeDetailsId(laptop.getEmployeeDetailsId());
			Laptop lap = this.laptopRepository.findById(laptop.getLaptopId()).get();
			map.put("empName", emp.getFullName());
			map.put("id", laptop.getId());
			map.put("empId", emp.getEmpId());
			map.put("srNo", lap.getSrNo());
			map.put("laptopId", lap.getId());
			if (laptop.getIssueDate() != null) {
				map.put("issueDate", laptop.getIssueDate().toString());
			} else {
				map.put("issueDate", null);
			}
			if (laptop.getReplaceDate() != null) {
				map.put("replaceDate", laptop.getReplaceDate().toString());
			} else {
				map.put("replaceDate", null);
			}
			if (laptop.getReturnDate() != null) {
				map.put("returnDate", laptop.getReturnDate().toString());
			} else {
				map.put("returnDate", null);
			}
			map.put("returnStatus", laptop.isReturnStatus());
			list.add(map);
		}

		return list;
	}

	@Override
	public Map<String, Object> getAssignedLaptops(Long id) {
		Map<String, Object> map = new HashMap();
		LaptopInventory laptop = laptopInventoryRepository.findById(id).get();
		EmployeeDetails emp = employeeDetailsRepository.findByEmployeeDetailsId(laptop.getEmployeeDetailsId());
		Laptop lap = this.laptopRepository.findById(laptop.getLaptopId()).get();
		map.put("empName", emp.getFullName());
		map.put("empId", emp.getEmpId());
		map.put("id", laptop.getId());
		map.put("srNo", lap.getSrNo());
		map.put("laptopId", lap.getId());
		if (laptop.getIssueDate() != null) {
			map.put("issueDate", laptop.getIssueDate().toString());
		} else {
			map.put("issueDate", null);
		}
		if (laptop.getReplaceDate() != null) {
			map.put("replaceDate", laptop.getReplaceDate().toString());
		} else {
			map.put("replaceDate", null);
		}
		if (laptop.getReturnDate() != null) {
			map.put("returnDate", laptop.getReturnDate().toString());
		} else {
			map.put("returnDate", null);
		}
		map.put("returnStatus", laptop.isReturnStatus());
		return map;
	}

	@Override
	public Map<String, String> editAssignedLaptops(LaptopInventory laptopInventory) {
		Map<String, String> map = new HashMap();
		LaptopInventory laptop = laptopInventoryRepository
				.findByEmployeeDetailsId(laptopInventory.getEmployeeDetailsId());
		laptop.setEmployeeDetailsId(laptopInventory.getEmployeeDetailsId());
		laptop.setIssueDate(laptopInventory.getIssueDate());
		laptop.setLaptopId(laptopInventory.getLaptopId());
		laptop.setReplaceDate(laptopInventory.getReplaceDate());
		laptop.setReturnDate(laptopInventory.getReturnDate());
		laptop.setReturnStatus(laptopInventory.isReturnStatus());
		laptopInventoryRepository.save(laptop);
		map.put("msg", "updated");
		return map;
	}

	@Override
	public List<Map<String, Object>> getAssignedLaptopsDetails(Long employeeDetailsId) {
		List<Map<String, Object>> list = new ArrayList();
		Map<String, Object> map = new HashMap();
		LaptopInventory laptop = laptopInventoryRepository.findByEmployeeDetailsId(employeeDetailsId);
		if (laptop != null) {
			EmployeeDetails emp = employeeDetailsRepository.findByEmployeeDetailsId(employeeDetailsId);
			Laptop lap = this.laptopRepository.findById(laptop.getLaptopId()).get();
			map.put("empName", emp.getFullName());
			map.put("empId", emp.getEmpId());
			map.put("id", laptop.getId());
			map.put("srNo", lap.getSrNo());
			map.put("laptopId", lap.getId());
			if (laptop.getIssueDate() != null) {
				map.put("issueDate", laptop.getIssueDate().toString());
			} else {
				map.put("issueDate", null);
			}
			if (laptop.getReplaceDate() != null) {
				map.put("replaceDate", laptop.getReplaceDate().toString());
			} else {
				map.put("replaceDate", null);
			}
			if (laptop.getReturnDate() != null) {
				map.put("returnDate", laptop.getReturnDate().toString());
			} else {
				map.put("returnDate", null);
			}
			map.put("returnStatus", laptop.isReturnStatus());
			list.add(map);
		}
		return list;
	}

	@Override
	public Object getAssignedLaptopsDetailsbyid(Long employeeDetailsId) {
		// LaptopInventory laptop=
		// laptopInventoryRepository.findByEmployeeDetailsId(employeeDetailsId);
		// return laptop ;
		Map<String, Object> map = new HashMap();
		LaptopInventory laptop = laptopInventoryRepository.findByEmployeeDetailsId(employeeDetailsId);
		if (laptop != null) {
			EmployeeDetails emp = employeeDetailsRepository.findByEmployeeDetailsId(employeeDetailsId);
			Laptop lap = this.laptopRepository.findById(laptop.getLaptopId()).get();
			map.put("empName", emp.getFullName());
			map.put("empId", emp.getEmpId());
			map.put("id", laptop.getId());
			map.put("srNo", lap.getSrNo());
			map.put("laptopId", lap.getId());
			if (laptop.getIssueDate() != null) {
				map.put("issueDate", laptop.getIssueDate().toString());
			} else {
				map.put("issueDate", null);
			}
			if (laptop.getReplaceDate() != null) {
				map.put("replaceDate", laptop.getReplaceDate().toString());
			} else {
				map.put("replaceDate", null);
			}
			if (laptop.getReturnDate() != null) {
				map.put("returnDate", laptop.getReturnDate().toString());
			} else {
				map.put("returnDate", null);
			}
			map.put("returnStatus", laptop.isReturnStatus());
		}
		return map;
	}
}
