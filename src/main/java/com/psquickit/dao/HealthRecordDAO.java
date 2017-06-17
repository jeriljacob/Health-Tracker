package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.HealthRecordDTO;

@Repository
public interface HealthRecordDAO extends JpaRepository<HealthRecordDTO, Long> {

	@Query("Select h from HealthRecordDTO h where h.id = :healthrecordid and h.user.id = :userid")
	HealthRecordDTO getByHealthRecordIdAndUserId(@Param("healthrecordid") Long healthRecordId, @Param("userid") Long userId);

	@Query("Select h from HealthRecordDTO h where h.user.id = :userid")
	List<HealthRecordDTO> listHealthRecordByUserId(@Param("userid") Long userId);

}
