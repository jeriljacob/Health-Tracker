package com.psquickit.managerImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import com.psquickit.common.HandledException;
import com.psquickit.dao.DegreeMasterDAO;
import com.psquickit.dao.DoctorDegreeDAO;
import com.psquickit.dao.DoctorMciDAO;
import com.psquickit.dao.DoctorSpecializationDAO;
import com.psquickit.dao.DoctorUserDAO;
import com.psquickit.dao.MCIMasterDAO;
import com.psquickit.dao.SpecializationMasterDAO;
import com.psquickit.dao.UserDAO;
import com.psquickit.dto.FileStoreDTO;
import com.psquickit.dto.UserDTO;
import com.psquickit.manager.AuthenticationManager;
import com.psquickit.manager.FileStoreManager;
import com.psquickit.manager.LoginManager;
import com.psquickit.pojo.user.UserLoginRequest;
import com.psquickit.pojo.user.UserLoginResponse;
import com.psquickit.util.ServiceUtils;

@Service
public class LoginManagerImpl implements LoginManager {

	@Autowired
	public UserDAO userDAO;

	@Autowired
	public DoctorUserDAO doctorUserDAO;

	@Autowired
	public DegreeMasterDAO degreeMasterDAO;

	@Autowired
	public MCIMasterDAO mciMasterDAO;

	@Autowired
	public SpecializationMasterDAO specializationMasterDAO;

	@Autowired
	public DoctorSpecializationDAO doctorSpecializationDAO;

	@Autowired
	public DoctorDegreeDAO doctorDegreeDAO;

	@Autowired
	public DoctorMciDAO doctorMciDAO;

	@Autowired
	public AuthenticationManager authManager;
	
	@Autowired
	public FileStoreManager fileStoreManager;
	
	@Override
	@Transactional
	public UserLoginResponse login(String secretToken, UserLoginRequest request) throws Exception {
		UserDTO userDTO = userDAO.checkAadhaarNumberExist(request.getAadhaarNumber());
		if (userDTO == null) {
			throw new HandledException("USER_DOES_NOT_EXIST", "User does not exist");
		}
		
		UserLoginResponse response = new UserLoginResponse();
		String authToken = authManager.generateAuthToken(userDTO.getId());
		response.setAuthToken(authToken);
		response.setFirstName(userDTO.getFirstName());
		response.setLastName(userDTO.getLastName());
		response.setUserType(userDTO.getUserType());
		
		FileStoreDTO profilePicFileStoreDTO = userDTO.getProfileImageFileStore();
		String profileImage = fileStoreManager.retrieveFile(profilePicFileStoreDTO).asCharSource(Charsets.UTF_8).read();
		response.setProfileImage(profileImage);
		
		return ServiceUtils.setResponse(response, true, "Login User");
	}

}
