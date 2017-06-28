package com.psquickit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psquickit.manager.DoctorUserManager;
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
import com.psquickit.util.ServiceUtils;

@RestController
@RequestMapping("/user/doctor")
public class DoctorUserController {

	@Autowired
	DoctorUserManager manager;

	@RequestMapping(value = "/register/multipart", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public @ResponseBody DoctorUserRegisterResponse registerUser(
			@RequestHeader(value="secretToken", required=true) String secretToken,
			@RequestPart(value="profilePic", required=false) MultipartFile profilePic,
			@RequestPart(value = "doctorRegistration", required=true) DoctorUserRegisterRequest request) {
		DoctorUserRegisterResponse response = new DoctorUserRegisterResponse();
		try {
			response = manager.registerDoctor(secretToken, request, profilePic);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Doctor User Registration", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody DoctorUserRegisterResponse registerUser(
			@RequestHeader(value="secretToken", required=true) String secretToken,
			@RequestBody DoctorUserRegisterRequest request) {
		DoctorUserRegisterResponse response = new DoctorUserRegisterResponse();
		try {
			response = manager.registerDoctor(secretToken, request);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Doctor User Registration", e);
		}
		return response;
	}

	@RequestMapping(value = "/update/multipart", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public @ResponseBody DoctorUserUpdateResponse updateUser(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestPart(value="profilePic", required=false) MultipartFile profilePic,
			@RequestPart(value = "doctorUpdate", required=true) DoctorUserUpdateRequest request) {
		DoctorUserUpdateResponse response = new DoctorUserUpdateResponse();
		try {
			response = manager.updateDoctor(authToken, request, profilePic);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Doctor User Registration", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody DoctorUserUpdateResponse updateUser(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestBody DoctorUserUpdateRequest request) {
		DoctorUserUpdateResponse response = new DoctorUserUpdateResponse();
		try {
			response = manager.updateDoctor(authToken, request);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Doctor User Registration", e);
		}
		return response;
	}

	@RequestMapping(value = "/list/degree", method = RequestMethod.GET)
	public @ResponseBody ListAllDegreeResponse listAllDegrees(
			@RequestHeader(value="secretToken", required=true) String secretToken) {
		ListAllDegreeResponse response = new ListAllDegreeResponse();
		try {
			response = manager.listAllDegree(secretToken);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "List All Degree", e);
		}
		return response;
	}

	@RequestMapping(value = "/list/mci", method = RequestMethod.GET)
	public @ResponseBody ListAllMciResponse listAllMci(
			@RequestHeader(value="secretToken", required=true) String secretToken) {
		ListAllMciResponse response = new ListAllMciResponse();
		try {
			response = manager.listAllMci(secretToken);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "List All MCI", e);
		}
		return response;
	}

	@RequestMapping(value = "/list/specialization", method = RequestMethod.GET)
	public @ResponseBody ListAllSpecializationResponse listAllSpecialization(
			@RequestHeader(value="secretToken", required=true) String secretToken) {
		ListAllSpecializationResponse response = new ListAllSpecializationResponse();
		try {
			response = manager.listAllSpecialization(secretToken);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "List All MCI", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/get/details", method = RequestMethod.GET)
	public @ResponseBody DoctorUserDetailResponse getDoctorUserDetail(
			@RequestHeader(value="authToken", required=true) String authToken) {
		DoctorUserDetailResponse response = new DoctorUserDetailResponse();
		try {
			response = manager.getDoctorUserDetail(authToken);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Get doctor user details", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody DoctorUsersResponse doctorList(@RequestHeader(value="authToken", required=true) String authToken,
			@RequestBody SearchUserRequest request) {
		DoctorUsersResponse response = new DoctorUsersResponse();
		try {
			response = manager.searchDoctor(authToken, request);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Search Doctor User List", e);
		}
		return response;
	}
	
}
