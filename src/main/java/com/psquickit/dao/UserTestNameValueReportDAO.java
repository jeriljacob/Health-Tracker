package com.psquickit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.UserTestNameValueReportDTO;

@Repository
public interface UserTestNameValueReportDAO extends JpaRepository<UserTestNameValueReportDTO, Long> {

}
