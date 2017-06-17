package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.PrescriptionFileDTO;

@Repository
public interface PrescriptionFileDAO extends JpaRepository<PrescriptionFileDTO, Long> {

	@Query("Select p from PrescriptionFileDTO p where p.userprescription.id = :userPrescriptionId")
	List<PrescriptionFileDTO> listPrescriptionFileByPrescriptionId(Long userPrescriptionId);

}
