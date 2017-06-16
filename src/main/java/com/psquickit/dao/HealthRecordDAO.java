package com.psquickit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.HealthRecordDTO;

@Repository
public interface HealthRecordDAO extends JpaRepository<HealthRecordDTO, Long> {

}
