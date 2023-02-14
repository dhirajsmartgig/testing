package com.smartgigInternal.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.entity.Laptop;
import com.smartgigInternal.entity.LaptopInventory;
import com.smartgigInternal.repository.EmployeeDetailsRepository;
import com.smartgigInternal.repository.LaptopInventoryRepository;
import com.smartgigInternal.repository.LaptopRepository;
import com.smartgigInternal.service.LaptopService;

@Service
public class LaptopServiceImpl implements LaptopService {

	@Autowired
	private LaptopRepository laptopRepository;

	@Autowired
	private LaptopInventoryRepository laptopInventoryRepository;

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Override
	public Map<String, String> saveLaptop(Laptop laptop) {
		Map<String, String> map = new HashMap();
		Laptop lap = this.laptopRepository.findBySrNo(laptop.getSrNo());
		if (lap == null) {
			laptopRepository.save(laptop);
			map.put("srNo", laptop.getSrNo());
			map.put("msg", "saved");
		} else {
			map.put("msg", "already present");
			map.put("srNo", lap.getSrNo());
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getAllLaptop(boolean status) {
		List<Laptop> list = laptopRepository.findByStatus(status);
		List<Map<String, Object>> mapList = new ArrayList();
		list.forEach(u -> {
			Map<String, Object> map = new HashMap();
			map.put("id", u.getId());
			map.put("srNo", u.getSrNo());
			map.put("company", u.getCompany());
			map.put("processor", u.getProcessor());
			map.put("modelProductId", u.getModelProductId());
			map.put("mtm", u.getMtm());
			map.put("chargerId", u.getChargerId());
			map.put("powercableId", u.getPowercableId());
			map.put("backPack", u.getBackPack());
			map.put("laptopType", u.getLaptopType());
			map.put("mailId", u.getMailId());
			map.put("lapPassword", u.getLapPassword());
			map.put("status", u.isStatus());
			map.put("ram", u.getRam());
			map.put("model", u.getModel());
			map.put("amountRent", u.getAmountRent());
			LaptopInventory lap = laptopInventoryRepository.findByLaptopIdAndReturnStatus(u.getId(), true);
			if (lap != null) {
				EmployeeDetails emp = this.employeeDetailsRepository
						.findByEmployeeDetailsId(lap.getEmployeeDetailsId());
				map.put("empName", emp.getFullName());
			} else {
				map.put("empName", null);
			}
			mapList.add(map);

		});
		return mapList;
	}

	@Override
	public Laptop getLaptop(Long id) {
		return laptopRepository.findById(id).get();
	}

	@Override
	public Map<String, String> updateLaptopDetails(Laptop laptop) {
		Map<String, String> map = new HashMap();
		Laptop lap = laptopRepository.findById(laptop.getId()).get();
		lap.setId(laptop.getId());
		lap.setSrNo(laptop.getSrNo());
		lap.setChargerId(laptop.getChargerId());
		lap.setBackPack(laptop.getBackPack());
		lap.setCompany(laptop.getCompany());
		lap.setLapPassword(laptop.getLapPassword());
		lap.setMailId(laptop.getMailId());
		lap.setModel(laptop.getModel());
		lap.setModelProductId(laptop.getModelProductId());
		lap.setLaptopType(laptop.getLaptopType());
		lap.setProcessor(laptop.getProcessor());
		lap.setMtm(laptop.getMtm());
		lap.setComment(laptop.getComment());
		lap.setStatus(laptop.isStatus());
		lap.setRam(laptop.getRam());
		lap.setPowercableId(laptop.getPowercableId());
		lap.setAmountRent(laptop.getAmountRent());
		laptopRepository.save(lap);
		map.put("msg", "updated");
		return map;
	}

	@Override
	public String changeStatus(Long id) {
		Laptop lap = laptopRepository.findById(id).get();
		lap.setStatus(false);
		laptopRepository.save(lap);
		return "deleted";
	}

	@Override
	public List<Map<String, Object>> laptopStock() {
		List<LaptopInventory> laptop = laptopInventoryRepository.findByReturnStatus(true);
		List<Long> idList = new ArrayList();
		laptop.forEach(u -> {
			idList.add(u.getLaptopId());
		});
		if (idList.isEmpty())
			idList.add(0L);
		List<Laptop> list = laptopRepository.findByStatusAndNotInAssigned(true, idList);
		List<Map<String, Object>> mapList = new ArrayList();
		list.forEach(u -> {
			Map<String, Object> map = new HashMap();
			map.put("id", u.getId());
			map.put("srNo", u.getSrNo());
			map.put("company", u.getCompany());
			map.put("processor", u.getProcessor());
			map.put("modelProductId", u.getModelProductId());
			map.put("mtm", u.getMtm());
			map.put("chargerId", u.getChargerId());
			map.put("powercableId", u.getPowercableId());
			map.put("backPack", u.getBackPack());
			map.put("laptopType", u.getLaptopType());
			map.put("mailId", u.getMailId());
			map.put("lapPassword", u.getLapPassword());
			map.put("status", u.isStatus());
			map.put("ram", u.getRam());
			map.put("model", u.getModel());
			map.put("amountRent", u.getAmountRent());
			LaptopInventory lap = laptopInventoryRepository.findByLaptopIdAndReturnStatus(u.getId(), true);
			if (lap != null) {
				EmployeeDetails emp = this.employeeDetailsRepository
						.findByEmployeeDetailsId(lap.getEmployeeDetailsId());
				map.put("empName", emp.getFullName());
			} else {
				map.put("empName", null);
			}
			mapList.add(map);

		});
		return mapList;
	}

	
	@Override
	public List<Map<String, Object>> getAssignedLaptop() {
		List<LaptopInventory> laptop = laptopInventoryRepository.findByReturnStatus(true);
		List<Long> idList = new ArrayList();
		laptop.forEach(u -> {
			idList.add(u.getLaptopId());
		});
		if (idList.isEmpty())
			idList.add(0L);
		List<Laptop> list = laptopRepository.findByStatusAndInAssigned(true, idList);
		List<Map<String, Object>> mapList = new ArrayList();
		list.forEach(u -> {
			Map<String, Object> map = new HashMap();
			map.put("id", u.getId());
			map.put("srNo", u.getSrNo());
			map.put("company", u.getCompany());
			map.put("processor", u.getProcessor());
			map.put("modelProductId", u.getModelProductId());
			map.put("mtm", u.getMtm());
			map.put("chargerId", u.getChargerId());
			map.put("powercableId", u.getPowercableId());
			map.put("backPack", u.getBackPack());
			map.put("laptopType", u.getLaptopType());
			map.put("mailId", u.getMailId());
			map.put("lapPassword", u.getLapPassword());
			map.put("status", u.isStatus());
			map.put("ram", u.getRam());
			map.put("model", u.getModel());
			map.put("amountRent", u.getAmountRent());
			LaptopInventory lap = laptopInventoryRepository.findByLaptopIdAndReturnStatus(u.getId(), true);
			if (lap != null) {
				EmployeeDetails emp = this.employeeDetailsRepository
						.findByEmployeeDetailsId(lap.getEmployeeDetailsId());
				map.put("empName", emp.getFullName());
			} else {
				map.put("empName", null);
			}
			mapList.add(map);

		});
		return mapList;
	}

	@Override
	public List<Map<String, String>> saveBulkLaptopDetails(MultipartFile bulkUploadExcel) {
		List<Map<String, String>> list = new ArrayList<>();
		try {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(bulkUploadExcel.getInputStream());
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			Row row;
			System.out.println(xssfSheet.getLastRowNum());
			for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
				row = xssfSheet.getRow(i);
				if (row != null) {
					String srNo = row.getCell(0).getStringCellValue();
					if (srNo != null) {
						String company = row.getCell(1).getStringCellValue();
						String model = row.getCell(2).getStringCellValue();
						String processor = row.getCell(3).getStringCellValue();
						String modelProductId = row.getCell(4).getStringCellValue();
						String mtm = "";
						if (row.getCell(5).getStringCellValue().toString() != null) {
							mtm = row.getCell(5).getStringCellValue().toString();
						}
						String chargerId = "";
						if (row.getCell(6).getStringCellValue().toString() != null) {
							chargerId = row.getCell(6).getStringCellValue().toString();
						}
						String powercableId = "";
						if (row.getCell(7).getStringCellValue().toString() != null) {
							powercableId = row.getCell(7).getStringCellValue().toString();
						}
						String backPack = row.getCell(8).getStringCellValue();
						String laptopType = row.getCell(9).getStringCellValue();
						String mailId = row.getCell(10).getStringCellValue();
						String lapPassword = row.getCell(11).getStringCellValue();
						String comment = row.getCell(12).getStringCellValue();
						String ram = row.getCell(13).getStringCellValue();
						boolean status = row.getCell(14).getBooleanCellValue();

						Laptop lap = this.laptopRepository.findBySrNo(srNo);
						if (lap == null) {
							Laptop laptop = new Laptop();
							laptop.setSrNo(srNo);
							laptop.setCompany(company);
							laptop.setModel(model);
							laptop.setProcessor(processor);
							laptop.setModelProductId(modelProductId);
							laptop.setMtm(mtm);
							laptop.setChargerId(chargerId);
							laptop.setPowercableId(powercableId);
							laptop.setBackPack(backPack);
							laptop.setLaptopType(laptopType);
							laptop.setMailId(mailId);
							laptop.setLapPassword(lapPassword);
							laptop.setComment(comment);
							laptop.setRam(ram);
							laptop.setStatus(status);
							this.laptopRepository.save(laptop);
						}
					} else {
						Map<String, String> map = new HashMap<>();
						map.put("srNo", srNo);
						list.add(map);
					}
				}
			}
			xssfWorkbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
