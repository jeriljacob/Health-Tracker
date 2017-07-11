package com.psquickit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psquickit.dto.UserDTO;

@Repository
public interface UserDAO extends JpaRepository<UserDTO, Long> {

	@Query("Select u from UserDTO u where u.aadhaarNumber = :aadhaarNumber")
	public UserDTO checkAadhaarNumberExist(@Param("aadhaarNumber") String aadhaarNumber);
	
	@Query("select u from UserDTO u where u.firstName like CONCAT('%',:firstName,'%')")
	public List<UserDTO> searchUserByFirstName(@Param("firstName") String searchText);
}
