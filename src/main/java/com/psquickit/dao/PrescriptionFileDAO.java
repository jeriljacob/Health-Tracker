package com.psquickit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.PrescriptionFileDTO;

@Repository
public interface PrescriptionFileDAO extends JpaRepository<PrescriptionFileDTO, Long> {

}
