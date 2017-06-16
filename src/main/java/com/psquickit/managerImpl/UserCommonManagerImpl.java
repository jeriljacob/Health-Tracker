package com.psquickit.managerImpl;

import com.psquickit.common.UserType;
import com.psquickit.dto.FileStoreDTO;
import com.psquickit.dto.UserDTO;
import com.psquickit.pojo.user.BasicUserDetails;

public class UserCommonManagerImpl {

	public static <T extends BasicUserDetails> UserDTO updateUserDTO(T userDetails, UserDTO userDTO, FileStoreDTO profilePicFileStoreDTO) {
		userDTO.setAadhaarNumber(userDetails.getAadhaarNumber());
		userDTO.setFirstName(userDetails.getFirstName());
		userDTO.setLastName(userDetails.getLastName());
		userDTO.setContactNumber(userDetails.getContactNo());
		userDTO.setAlternateContactNumber(userDetails.getAlternateContactNo());
		userDTO.setAlternateAddress(userDetails.getAlternateAddress());
		userDTO.setPermanentAddress(userDetails.getPermanentAddress());
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
		details.setAlternateAddress(userDTO.getAlternateAddress());
		details.setPermanentAddress(userDTO.getPermanentAddress());
		details.setEmail(userDTO.getEmail());
		details.setUserType(UserType.fromName(userDTO.getUserType()).getName());
		details.setProfileImg(profileImage);
		return details;
	}

	public static <T extends BasicUserDetails> UserDTO createUserDTO(T userDetails, FileStoreDTO profilePicFileStoreDTO) {
		UserDTO userDTO = new UserDTO();
		updateUserDTO(userDetails, userDTO, profilePicFileStoreDTO);		
		return userDTO;
	}
}
