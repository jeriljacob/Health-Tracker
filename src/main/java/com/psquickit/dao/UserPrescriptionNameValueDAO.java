package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.UserPrescriptionNameValueDTO;

@Repository
public interface UserPrescriptionNameValueDAO extends JpaRepository<UserPrescriptionNameValueDTO, Long> {

	@Query("Select u from UserPrescriptionNameValueDTO u where u.healthrecord.id = :healthRecordId ")
	List<UserPrescriptionNameValueDTO> listUserPrescriptionNameValueByPrescriptionId(@Param("healthRecordId") Long healthRecordId);
}
