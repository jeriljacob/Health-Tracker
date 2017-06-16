package com.psquickit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.DiagnosisReportFileDTO;

@Repository
public interface DiagnosisReportFileDAO extends JpaRepository<DiagnosisReportFileDTO, Long> {

}
