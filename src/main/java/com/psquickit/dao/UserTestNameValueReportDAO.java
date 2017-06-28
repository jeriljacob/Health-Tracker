package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.UserTestNameValueReportDTO;

@Repository
public interface UserTestNameValueReportDAO extends JpaRepository<UserTestNameValueReportDTO, Long> {

	@Query("Select u from UserTestNameValueReportDTO u where u.healthrecord.id = :healthrecordid")
	List<UserTestNameValueReportDTO> listUserTestNameValueReportByHealthRecordId(@Param("healthrecordid") Long healthRecordId);

}
