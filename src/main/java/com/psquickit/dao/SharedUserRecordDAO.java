package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.SharedUserRecordDTO;

@Repository
public interface SharedUserRecordDAO extends JpaRepository<SharedUserRecordDTO, Long> {

	@Query("Select s from SharedUserRecordDTO s where s.sharedBy.id = :sharedById")
	List<SharedUserRecordDTO> listRecordsSharedBy(@Param("sharedById") long userId);

	@Query("Select s from SharedUserRecordDTO s where s.sharedTo.id = :sharedToId")
	List<SharedUserRecordDTO> listRecordsSharedTo(@Param("sharedToId") long userId);

}
