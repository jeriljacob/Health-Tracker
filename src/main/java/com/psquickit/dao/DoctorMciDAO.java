package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.DoctorMciDTO;

@Repository
public interface DoctorMciDAO extends JpaRepository<DoctorMciDTO, Long> {

	@Query("Select d from DoctorMciDTO d where d.doctoruser.id = :id")
	public List<DoctorMciDTO> listMciByDoctorId(@Param("id") Long id);
}
