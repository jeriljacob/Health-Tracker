package com.psquickit.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.psquickit.pojo.health.record.ListHealthRecordResponse;
import com.psquickit.pojo.health.record.UpdateTestNameValue;
import com.psquickit.pojo.health.record.UploadDiagnosisResponse;
import com.psquickit.pojo.health.record.UploadPrescriptionResponse;
import com.psquickit.util.ServiceUtils;

@RestController
@RequestMapping("/healthrecord")
public class HealthRecordController {
	
	@Autowired
	HealthRecordManager manager;
	
	@RequestMapping(value = "/list/healthrecord", method = RequestMethod.GET)
	public @ResponseBody ListHealthRecordResponse listHealthRecord(
			@RequestHeader(value="authToken", required=true) String authToken
			) {
		ListHealthRecordResponse response = new ListHealthRecordResponse();
		try {
			response = manager.listHealthRecord(authToken);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "List health record", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/get/healthrecord/{healthrecordid}", method = RequestMethod.GET)
	public @ResponseBody GetHealthRecordResponse getHealthRecord(
			@RequestHeader(value="authToken", required=true) String authToken,
			@PathVariable(value="healthrecordid") String healthRecordId
			) {
		GetHealthRecordResponse response = new GetHealthRecordResponse();
		try {
			response = manager.getHealthRecord(authToken, Long.parseLong(healthRecordId));
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Get health record", e);
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
			return ServiceUtils.setResponse(response, false, "Add test name values", e);
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
			return ServiceUtils.setResponse(response, false, "Update test name values", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/get/testnamevaluereport/{healthrecordid}", method = RequestMethod.GET)
	public @ResponseBody GetTestNameValueReportResponse getTestNameValue(
			@RequestHeader(value="authToken", required=true) String authToken,
			@PathVariable(value="healthrecordid") String healthRecordId
			) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		try {
			response = manager.getTestNameValueReport(authToken, Long.parseLong(healthRecordId));
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Get test name value report", e);
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
			return ServiceUtils.setResponse(response, false, "Delete test", e);
		}
		return response;
	}
	
	//TODO: add delete for subtest, and health records as well
	
	@RequestMapping(value = "/add/testnamereport", method = RequestMethod.POST)
	public @ResponseBody GetTestNameValueReportResponse addTestNameValue(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestParam(value="healthRecordId", required=false) String healthRecordId,
			@RequestParam(value="healthRecordDate", required=false) Date healthRecordDate,
			@RequestParam(value="testname", required=true) String testName,
			@RequestPart(value="testreport", required=true) MultipartFile[] testReports
			) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		try {
			response = manager.addTestNameReport(authToken, healthRecordId, healthRecordDate, testName, testReports);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Add test report", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/get/testnamereport/{id}", method = RequestMethod.GET)
	public void getTestNameReport(
			@RequestHeader(value="authToken", required=true) String authToken,
			@PathVariable(value="id") String id,
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
			@RequestParam(value="healthRecordId", required=false) String healthRecordId,
			@RequestParam(value="healthRecordDate", required=false) @DateTimeFormat(pattern="MMddyyyy") Date healthRecordDate,
			@RequestPart(value = "prescription", required=true) MultipartFile[] prescriptions
			) {
		UploadPrescriptionResponse response = new UploadPrescriptionResponse();
		try {
			response = manager.uploadPrescription(authToken, healthRecordId, healthRecordDate, prescriptions);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload prescription", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/upload/diagnosis", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public @ResponseBody UploadDiagnosisResponse uploadDiagnosis(
			@RequestHeader(value="authToken", required=true) String authToken,
			@RequestParam(value="healthRecordId", required=false) String healthRecordId,
			@RequestParam(value="healthRecordDate", required=false) @DateTimeFormat(pattern="MMddyyyy") Date healthRecordDate,
			@RequestParam(value="diagnosisName", required=true) String diagnosisName,
			@RequestPart(value = "diagnosis", required=true) MultipartFile[] diagnosises
			) {
		UploadDiagnosisResponse response = new UploadDiagnosisResponse();
		try {
			response = manager.uploadDiagnosis(authToken, healthRecordId, healthRecordDate, diagnosisName, diagnosises);
		} catch (Exception e) {
			return ServiceUtils.setResponse(response, false, "Upload diagnosis", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/get/prescription/{id}", method = RequestMethod.GET)
	public void getPrescription(
			@RequestHeader(value="authToken", required=true) String authToken,
			@PathVariable(value="id") String id,
			final HttpServletResponse httpResponse) {
		try {
			long prescriptionId = Long.parseLong(id);
			manager.getPrescription(authToken, prescriptionId, httpResponse);
		} catch (Exception e) {
			setHttpException(e, httpResponse);
		}
	}
	
	@RequestMapping(value = "/get/diagnosis/{id}", method = RequestMethod.GET)
	public void getDiagnosis(
			@RequestHeader(value="authToken", required=true) String authToken,
			@PathVariable(value="id") String id,
			final HttpServletResponse httpResponse
			) {
		try {
			long diagnosisId = Long.parseLong(id);
			manager.getDiagnosis(authToken, diagnosisId, httpResponse);
		} catch (Exception e) {
			setHttpException(e, httpResponse);
		}
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
			return ServiceUtils.setResponse(response, false, "Delete prescription", e);
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
			return ServiceUtils.setResponse(response, false, "Delete diagnosis", e);
		}
		return response;
	}
	
	public static void setHttpException(Exception e, HttpServletResponse httpResponse) {
		httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		httpResponse.setHeader("Exception", e.getMessage());
	}
}
