package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.UserDiagnosisReportDTO;

@Repository
public interface UserDiagnosisReportDAO extends JpaRepository<UserDiagnosisReportDTO, Long> {

	@Query("Select u from UserDiagnosisReportDTO u where u.healthrecord.id = :healthrecordid and u.healthrecord.user.id = :userid")
	List<UserDiagnosisReportDTO> listUserDiagnosisReportByHealthRecordIdAndUserId(@Param("healthrecordid") Long healthRecordId, @Param("userid") Long userId);

}
