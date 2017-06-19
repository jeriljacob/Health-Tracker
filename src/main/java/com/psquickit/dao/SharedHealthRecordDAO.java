package com.psquickit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.SharedHealthRecordDTO;

@Repository
public interface SharedHealthRecordDAO extends JpaRepository<SharedHealthRecordDTO, Long> {

}
