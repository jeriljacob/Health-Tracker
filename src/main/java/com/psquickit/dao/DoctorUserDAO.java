package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.DoctorUserDTO;
import com.psquickit.dto.UserDTO;

@Repository
public interface DoctorUserDAO extends JpaRepository<DoctorUserDTO, Long> {

	@Query("Select d from DoctorUserDTO d where d.user.id = :id")
	public DoctorUserDTO getDoctorUserByUserId(@Param("id") Long id);
	
	@Query("select d from DoctorUserDTO d where d.user.firstName like CONCAT('%',:firstName,'%')")
	public List<DoctorUserDTO> searchDoctorUserByFirstName(@Param("firstName") String searchText);

}
