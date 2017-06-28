package com.psquickit.manager;

import org.springframework.web.multipart.MultipartFile;

import com.psquickit.pojo.user.DoctorUserDetailResponse;
import com.psquickit.pojo.user.DoctorUserRegisterRequest;
import com.psquickit.pojo.user.DoctorUserRegisterResponse;
import com.psquickit.pojo.user.DoctorUserUpdateRequest;
import com.psquickit.pojo.user.DoctorUserUpdateResponse;
import com.psquickit.pojo.user.DoctorUsersResponse;
import com.psquickit.pojo.user.ListAllDegreeResponse;
import com.psquickit.pojo.user.ListAllMciResponse;
import com.psquickit.pojo.user.ListAllSpecializationResponse;
import com.psquickit.pojo.user.SearchUserRequest;

public interface DoctorUserManager {

	DoctorUserRegisterResponse registerDoctor(String secretToken, DoctorUserRegisterRequest request, MultipartFile profilePic)
			throws Exception;

	DoctorUserUpdateResponse updateDoctor(String authToken, DoctorUserUpdateRequest request, MultipartFile profilePic) throws Exception;

	ListAllDegreeResponse listAllDegree(String secretToken) throws Exception;

	ListAllMciResponse listAllMci(String secretToken) throws Exception;

	ListAllSpecializationResponse listAllSpecialization(String secretToken) throws Exception;
	
	DoctorUserDetailResponse getDoctorUserDetail(String authToken) throws Exception;

	DoctorUserRegisterResponse registerDoctor(String secretToken, DoctorUserRegisterRequest request) throws Exception;

	DoctorUserUpdateResponse updateDoctor(String authToken, DoctorUserUpdateRequest request) throws Exception;

	DoctorUsersResponse searchDoctor(String authToken, SearchUserRequest request) throws Exception;

}
