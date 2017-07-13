package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.PresciptionRxDTO;

@Repository
public interface PrescriptionRxDAO extends JpaRepository<PresciptionRxDTO, Long> {
	@Query("Select s from PresciptionRxDTO s where s.userprescriptionnamevalue.id = :userPrescriptonNameValueId")
	List<PresciptionRxDTO> listPrescriptionByUserTestNameValueReportId(
			@Param("userPrescriptonNameValueId") Long userPrescriptonNameValueId);
	
}
