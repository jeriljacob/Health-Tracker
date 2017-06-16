package com.psquickit.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.psquickit.manager.HealthRecordManager;
import com.psquickit.pojo.health.record.AddTestNameValue;
import com.psquickit.pojo.health.record.DeleteDiagnosisResponse;
import com.psquickit.pojo.health.record.DeletePrescriptionResponse;
import com.psquickit.pojo.health.record.DeleteTestResponse;
import com.psquickit.pojo.health.record.GetHealthRecordResponse;
import com.psquickit.pojo.health.record.GetTestNameValueReportResponse;
import com.psquickit.pojo.health.record.UpdateTestNameValue;
import com.psquickit.pojo.health.record.UploadDiagnosisResponse;
import com.psquickit.pojo.health.record.UploadHealthRecordResponse;
import com.psquickit.pojo.health.record.UploadPrescriptionResponse;
import com.psquickit.util.ServiceUtils;

@RestController
@RequestMapping("/healthrecord")
public class HealthRecordController {
	
	@Autowired
	HealthRecordManager manager;
	
	//in this request, we will only return the file
	@RequestMapping(value = "/get/healthrecord", method = RequestMethod.GET)
	public @ResponseBody GetHealthRecordResponse getHealthRecord(
			@RequestHeader(value="authToken", required=true) String authToken
			) {
		GetHealthRecordResponse response = new GetHealthRecordResponse();
		try {
			response = manager.getHealthRecord(authToken);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/add/testnamevalue", method = RequestMethod.POST)
	public @ResponseBody GetTestNameValueReportResponse addTestNameValue(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestBody AddTestNameValue request
			) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		try {
			response = manager.addTestNameValue(authToken, request);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/update/testnamevalue", method = RequestMethod.POST)
	public @ResponseBody GetTestNameValueReportResponse updateTestNameValue(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestBody UpdateTestNameValue request
			) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		try {
			response = manager.updateTestNameValue(authToken, request);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/get/testnamevaluereport", method = RequestMethod.GET)
	public @ResponseBody GetTestNameValueReportResponse getTestNameValue(
			@RequestHeader(value="authToken", required=true) String authToken
			) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		try {
			response = manager.getTestNameValueReport(authToken);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/delete/test", method = RequestMethod.DELETE)
	public @ResponseBody DeleteTestResponse deleteTest(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestParam(value="testid") String[] ids
			) {
		DeleteTestResponse response = new DeleteTestResponse();
		try {
			List<Long> testIds = Lists.newArrayList(); 
			for (String id: ids) {
				testIds.add(Long.parseLong(id));
			}
			response = manager.deleteTest(authToken, testIds);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/add/testnamereport", method = RequestMethod.POST)
	public @ResponseBody GetTestNameValueReportResponse addTestNameValue(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestPart(value="testname", required=true) String testName,
			@RequestPart(value="testreport", required=true) MultipartFile testReport
			) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		try {
			response = manager.addTestNameReport(authToken, testName, testReport);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/get/testnamereport/{id}", method = RequestMethod.GET)
	public void getTestNameReport(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestParam(value="id") String id,
			final HttpServletResponse httpResponse) {
		try {
			long testReportId = Long.parseLong(id);
			manager.getTestNameReport(authToken, testReportId, httpResponse);
		} catch (Exception e) {
			setHttpException(e, httpResponse);
		}
	}
	
	@RequestMapping(value = "/upload/prescription", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public @ResponseBody UploadPrescriptionResponse uploadPrescription(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestPart(value = "prescription", required=true) MultipartFile[] prescription
			) {
		UploadPrescriptionResponse response = new UploadPrescriptionResponse();
		try {
			response = manager.uploadPrescription(authToken, prescription);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/upload/diagnosis", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public @ResponseBody UploadDiagnosisResponse uploadDiagnosis(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestPart(value = "diagnosis", required=true) MultipartFile[] diagnosis
			) {
		UploadDiagnosisResponse response = new UploadDiagnosisResponse();
		try {
			response = manager.uploadDiagnosis(authToken, diagnosis);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/get/prescription/{id}", method = RequestMethod.GET)
	public void getPrescription(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestParam(value="id") String id,
			final HttpServletResponse httpResponse) {
		try {
			long prescriptionId = Long.parseLong(id);
			manager.getPrescription(authToken, prescriptionId, httpResponse);
		} catch (Exception e) {
			setHttpException(e, httpResponse);
		}
	}
	
	@RequestMapping(value = "/get/diagnosis/{id}", method = RequestMethod.GET)
	public @ResponseBody UploadHealthRecordResponse getDiagnosis(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestParam(value="id") String id,
			final HttpServletResponse httpResponse
			) {
		UploadHealthRecordResponse response = new UploadHealthRecordResponse();
		try {
			long diagnosisId = Long.parseLong(id);
			manager.getDiagnosis(authToken, diagnosisId, httpResponse);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/delete/prescription", method = RequestMethod.DELETE)
	public @ResponseBody DeletePrescriptionResponse deletePrescription(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestParam(value="id") String[] ids
			) {
		DeletePrescriptionResponse response = new DeletePrescriptionResponse();
		try {
			List<Long> prescriptionIds = Lists.newArrayList(); 
			for (String id: ids) {
				prescriptionIds.add(Long.parseLong(id));
			}			
			response = manager.deletePrescription(authToken, prescriptionIds);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/delete/diagnosis", method = RequestMethod.DELETE)
	public @ResponseBody DeleteDiagnosisResponse deleteDiagnosis(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestParam(value="id") String[] ids
			) {
		DeleteDiagnosisResponse response = new DeleteDiagnosisResponse();
		try {
			List<Long> diagnosisIds = Lists.newArrayList(); 
			for (String id: ids) {
				diagnosisIds.add(Long.parseLong(id));
			}
			response = manager.deleteDiagnosis(authToken, diagnosisIds);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload health record", e);
		}
		return response;
	}
	
	public static void setHttpException(Exception e, HttpServletResponse httpResponse) {
		httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		httpResponse.setHeader("Exception", e.getMessage());
	}
}
