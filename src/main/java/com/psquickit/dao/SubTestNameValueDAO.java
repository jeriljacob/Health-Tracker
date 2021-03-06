package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.SubTestNameValueDTO;

@Repository
public interface SubTestNameValueDAO extends JpaRepository<SubTestNameValueDTO, Long> {

	@Query("Select s from SubTestNameValueDTO s where s.usertestnamevaluereport.id = :userTestNameValueReportId")
	List<SubTestNameValueDTO> listSubTestNameValueByUserTestNameValueReportId(
			@Param("userTestNameValueReportId") Long userTestNameValueReportId);

}
