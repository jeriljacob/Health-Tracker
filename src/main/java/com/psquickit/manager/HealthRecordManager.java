package com.psquickit.manager;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

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

public interface HealthRecordManager {

	GetHealthRecordResponse getHealthRecord(String authToken, long healthRecordId) throws Exception;

	GetTestNameValueReportResponse addTestNameValue(String authToken, AddTestNameValue request) throws Exception;

	GetTestNameValueReportResponse updateTestNameValue(String authToken, UpdateTestNameValue request) throws Exception;

	GetTestNameValueReportResponse getTestNameValueReport(String authToken, long healthRecordId) throws Exception;

	DeleteTestResponse deleteTest(String authToken, List<Long> ids) throws Exception;

	GetTestNameValueReportResponse addTestNameReport(String authToken, String healthRecordId,
			Date healthRecordDate, String testName, MultipartFile[] testReports) throws Exception;

	void getTestNameReport(String authToken, long testReportId, HttpServletResponse httpResponse) throws Exception;

	UploadPrescriptionResponse uploadPrescription(String authToken, String healthRecordId,
			Date healthRecordDate, MultipartFile[] prescriptions) throws Exception;

	UploadDiagnosisResponse uploadDiagnosis(String authToken, String healthRecordId,
			Date healthRecordDate, String diagnosisName, MultipartFile[] diagnosises) throws Exception;

	void getPrescription(String authToken, long prescriptionId, HttpServletResponse httpResponse) throws Exception;

	void getDiagnosis(String authToken, long diagnosisId, HttpServletResponse httpResponse) throws Exception;

	DeletePrescriptionResponse deletePrescription(String authToken, List<Long> prescriptionIds) throws Exception;

	DeleteDiagnosisResponse deleteDiagnosis(String authToken, List<Long> diagnosisIds) throws Exception;

	ListHealthRecordResponse listHealthRecord(String authToken) throws Exception;
}
