package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.DoctorDegreeDTO;

@Repository
public interface DoctorDegreeDAO extends JpaRepository<DoctorDegreeDTO, Long> {

	@Query("Select d from DoctorDegreeDTO d where d.doctoruser.id = :id")
	public List<DoctorDegreeDTO> listDegreeByDoctorId(@Param("id") Long id);
}
