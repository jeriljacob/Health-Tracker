package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.HealthRecordDTO;
import com.psquickit.dto.SharedHealthRecordDTO;

@Repository
public interface SharedHealthRecordDAO extends JpaRepository<SharedHealthRecordDTO, Long> {

	@Query("Select s from SharedHealthRecordDTO s where s.healthrecord.id = :healthRecordId")
	SharedHealthRecordDTO findSharedHealthRecordByHealthRecordId(@Param("healthRecordId") long healthRecordId);

	@Query("Select s from SharedHealthRecordDTO s where s.healthrecord.id in (:healthRecordIdsShared) and s.shareduserrecord.sharedTo.id = :sharedToUserId")
	List<HealthRecordDTO> listSharedHealthRecordsIn(@Param("healthRecordIdsShared") List<Long> healthRecordIdsShared, @Param("sharedToUserId") long sharedToUserId);

}
