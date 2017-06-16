package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.DoctorClinicAddressDTO;

@Repository
public interface DoctorClinicAddressDAO extends JpaRepository<DoctorClinicAddressDTO, Long> {

	@Query("Select d from DoctorClinicAddressDTO d where d.doctoruser.id = :id")
	List<DoctorClinicAddressDTO> listDoctorClinicAddressByDoctorId(long doctorId);

}
