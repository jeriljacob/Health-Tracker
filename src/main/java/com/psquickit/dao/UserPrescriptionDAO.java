package com.psquickit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.UserPrescriptionDTO;

@Repository
public interface UserPrescriptionDAO extends JpaRepository<UserPrescriptionDTO, Long> {

}
