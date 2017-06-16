package com.psquickit.managerImpl;

import com.psquickit.common.UserType;
import com.psquickit.dto.AddressDTO;
import com.psquickit.dto.FileStoreDTO;
import com.psquickit.dto.UserDTO;
import com.psquickit.pojo.user.Address;
import com.psquickit.pojo.user.BasicUserDetails;

public class UserCommonManagerImpl {

	public static <T extends BasicUserDetails> UserDTO updateUserDTO(T userDetails, UserDTO userDTO, FileStoreDTO profilePicFileStoreDTO) {
		userDTO.setAadhaarNumber(userDetails.getAadhaarNumber());
		userDTO.setFirstName(userDetails.getFirstName());
		userDTO.setLastName(userDetails.getLastName());
		userDTO.setContactNumber(userDetails.getContactNo());
		userDTO.setAlternateContactNumber(userDetails.getAlternateContactNo());
		
		AddressDTO alternateAddressDTO = new AddressDTO();
		alternateAddressDTO.setCity(userDetails.getAlternateAddress().getCity());
		alternateAddressDTO.setStreet(userDetails.getAlternateAddress().getStreet());
		alternateAddressDTO.setDistrict(userDetails.getAlternateAddress().getDistrict());
		alternateAddressDTO.setState(userDetails.getAlternateAddress().getState());
		alternateAddressDTO.setPincode(userDetails.getAlternateAddress().getPincode());
		
		AddressDTO permanentAddressDTO = new AddressDTO();
		permanentAddressDTO.setCity(userDetails.getPermanentAddress().getCity());
		permanentAddressDTO.setStreet(userDetails.getPermanentAddress().getStreet());
		permanentAddressDTO.setDistrict(userDetails.getPermanentAddress().getDistrict());
		permanentAddressDTO.setState(userDetails.getPermanentAddress().getState());
		permanentAddressDTO.setPincode(userDetails.getPermanentAddress().getPincode());
		
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

	public static <T extends BasicUserDetails> UserDTO createUserDTO(T userDetails, FileStoreDTO profilePicFileStoreDTO) {
		UserDTO userDTO = new UserDTO();
		updateUserDTO(userDetails, userDTO, profilePicFileStoreDTO);		
		return userDTO;
	}
}
