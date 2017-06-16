package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.TestReportFileDTO;

@Repository
public interface TestReportFileDAO extends JpaRepository<TestReportFileDTO, Long> {

	@Query("Select t from TestReportFileDTO t where t.usertestnamevaluereport.id = :userTestNameValueReportId")
	List<TestReportFileDTO> listTestReportFileByUserTestNameValueReportId(
			@Param("userTestNameValueReportId") Long userTestNameValueReportId);

}
