package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.UserPrescriptionDTO;

@Repository
public interface UserPrescriptionDAO extends JpaRepository<UserPrescriptionDTO, Long> {

	@Query("Select u from UserPrescriptionDTO u where u.healthrecord.id = :healthrecordid and u.healthrecord.user.id = :userid")
	List<UserPrescriptionDTO> listUserPrescriptionByHealthRecordIdAndUserId(@Param("healthrecordid") Long healthRecordId, @Param("userid") Long userId);

}
