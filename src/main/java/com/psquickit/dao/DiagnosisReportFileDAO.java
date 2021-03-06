package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.DiagnosisReportFileDTO;

@Repository
public interface DiagnosisReportFileDAO extends JpaRepository<DiagnosisReportFileDTO, Long> {

	@Query("Select d from DiagnosisReportFileDTO d where d.userdiagnosisreport.id = :userDiagnosisReportId")
	List<DiagnosisReportFileDTO> listDiagnosisReportFileByDiagnosisId(@Param("userDiagnosisReportId") Long userDiagnosisReportId);

}
