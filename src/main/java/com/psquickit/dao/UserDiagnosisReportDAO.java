package com.psquickit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.UserDiagnosisReportDTO;

@Repository
public interface UserDiagnosisReportDAO extends JpaRepository<UserDiagnosisReportDTO, Long> {

}
