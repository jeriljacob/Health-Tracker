package com.psquickit.managerImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.psquickit.common.UserType;
import com.psquickit.dao.AddressDAO;
import com.psquickit.dto.AddressDTO;
import com.psquickit.dto.FileStoreDTO;
import com.psquickit.dto.UserDTO;
import com.psquickit.pojo.user.Address;
import com.psquickit.pojo.user.BasicUserDetails;

public class UserCommonManagerImpl {

	@Autowired
	AddressDAO addressDAO;
	
	public static <T extends BasicUserDetails> UserDTO updateUserDTO(T userDetails, UserDTO userDTO, FileStoreDTO profilePicFileStoreDTO,
			AddressDTO alternateAddressDTO, AddressDTO permanentAddressDTO) {
		userDTO.setAadhaarNumber(userDetails.getAadhaarNumber());
		userDTO.setFirstName(userDetails.getFirstName());
		userDTO.setLastName(userDetails.getLastName());
		userDTO.setContactNumber(userDetails.getContactNo());
		userDTO.setAlternateContactNumber(userDetails.getAlternateContactNo());
		
		userDTO.setAlternateAddress(alternateAddressDTO);
		userDTO.setPermanentAddress(permanentAddressDTO);
		userDTO.setEmail(userDetails.getEmail());
		userDTO.setUserType(UserType.fromName(userDetails.getUserType()).getName());
		userDTO.setProfileImageFileStore(profilePicFileStoreDTO);
		return userDTO;
	}

	public static <T extends BasicUserDetails> T toBasicUserDetails(T details, UserDTO userDTO, String profileImage) {
		details.setAadhaarNumber(userDTO.getAadhaarNumber());
		details.setFirstName(userDTO.getFirstName());
		details.setLastName(userDTO.getLastName());
		details.setContactNo(userDTO.getContactNumber());
		details.setAlternateContactNo(userDTO.getAlternateContactNumber());
		details.setAlternateAddress(toAddress(userDTO.getAlternateAddress()));
		details.setPermanentAddress(toAddress(userDTO.getPermanentAddress()));
		details.setEmail(userDTO.getEmail());
		details.setUserType(UserType.fromName(userDTO.getUserType()).getName());
		details.setProfileImg(profileImage);
		return details;
	}
	
	public static <T extends BasicUserDetails> AddressDTO populateAlternateAddressDTO (T userDetails) {
		AddressDTO alternateAddressDTO = new AddressDTO();
		return populateAlternateAddressDTO(userDetails, alternateAddressDTO);
	}
	
	public static <T extends BasicUserDetails> AddressDTO populateAlternateAddressDTO(T userDetails, AddressDTO alternateAddressDTO) {
		alternateAddressDTO.setCity(userDetails.getAlternateAddress().getCity());
		alternateAddressDTO.setStreet(userDetails.getAlternateAddress().getStreet());
		alternateAddressDTO.setDistrict(userDetails.getAlternateAddress().getDistrict());
		alternateAddressDTO.setState(userDetails.getAlternateAddress().getState());
		alternateAddressDTO.setPincode(userDetails.getAlternateAddress().getPincode());
		return alternateAddressDTO;
	}
	
	public static <T extends BasicUserDetails> AddressDTO populatePermanentAddressDTO (T userDetails) {
		AddressDTO permanentAddressDTO = new AddressDTO();
		return populateAlternateAddressDTO(userDetails, permanentAddressDTO);
	}
	
	public static <T extends BasicUserDetails> AddressDTO populatePermanentAddressDTO(T userDetails, AddressDTO permanentAddressDTO) {
		permanentAddressDTO.setCity(userDetails.getPermanentAddress().getCity());
		permanentAddressDTO.setStreet(userDetails.getPermanentAddress().getStreet());
		permanentAddressDTO.setDistrict(userDetails.getPermanentAddress().getDistrict());
		permanentAddressDTO.setState(userDetails.getPermanentAddress().getState());
		permanentAddressDTO.setPincode(userDetails.getPermanentAddress().getPincode());
		return permanentAddressDTO;
	}

	private static Address toAddress(AddressDTO dto) {
		Address address = new Address();
        address.setId(Long.toString(dto.getId()));
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setDistrict(dto.getDistrict());
        address.setState(dto.getState());
        address.setPincode(dto.getPincode());
        return address;
	}

	public static <T extends BasicUserDetails> UserDTO createUserDTO(T userDetails, FileStoreDTO profilePicFileStoreDTO,
			AddressDTO alternateAddressDTO, AddressDTO permanentAddressDTO) {
		UserDTO userDTO = new UserDTO();
		updateUserDTO(userDetails, userDTO, profilePicFileStoreDTO, alternateAddressDTO, permanentAddressDTO);		
		return userDTO;
	}
}
