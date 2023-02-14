package com.smartgigInternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartgigInternal.entity.Laptop;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {

	Laptop findBySrNo(String srNo);

	List<Laptop> findByStatus(boolean status);

	@Query(value = "select eg from Laptop eg where eg.status=?1 and eg.id not in(?2)")
	List<Laptop> findByStatusAndNotInAssigned(boolean b, List<Long> idList);

	@Query(value = "select eg from Laptop eg where eg.status=?1 and eg.id in(?2)")
	List<Laptop> findByStatusAndInAssigned(boolean b, List<Long> idList);


}
