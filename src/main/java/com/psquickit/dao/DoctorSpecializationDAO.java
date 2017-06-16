package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.DoctorSpecializationDTO;

@Repository
public interface DoctorSpecializationDAO extends JpaRepository<DoctorSpecializationDTO, Long> {

	@Query("Select d from DoctorSpecializationDTO d where d.doctoruser.id = :id")
	public List<DoctorSpecializationDTO> listSpecializationByDoctorId(@Param("id") Long id);

}
