package com.psquickit.manager;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.psquickit.pojo.health.record.AddTestNameValue;
import com.psquickit.pojo.health.record.DeleteDiagnosisResponse;
import com.psquickit.pojo.health.record.DeletePrescriptionResponse;
import com.psquickit.pojo.health.record.DeleteTestResponse;
import com.psquickit.pojo.health.record.GetHealthRecordResponse;
import com.psquickit.pojo.health.record.GetTestNameValueReportResponse;
import com.psquickit.pojo.health.record.UpdateTestNameValue;
import com.psquickit.pojo.health.record.UploadDiagnosisResponse;
import com.psquickit.pojo.health.record.UploadPrescriptionResponse;

public interface HealthRecordManager {

	GetHealthRecordResponse getHealthRecord(String authToken);

	GetTestNameValueReportResponse addTestNameValue(String authToken, AddTestNameValue request);

	GetTestNameValueReportResponse updateTestNameValue(String authToken, UpdateTestNameValue request);

	GetTestNameValueReportResponse getTestNameValueReport(String authToken);

	DeleteTestResponse deleteTest(String authToken, List<Long> ids);

	GetTestNameValueReportResponse addTestNameReport(String authToken, String testName, MultipartFile testReport);

	void getTestNameReport(String authToken, long testReportId, HttpServletResponse httpResponse);

	UploadPrescriptionResponse uploadPrescription(String authToken, MultipartFile[] prescription);

	UploadDiagnosisResponse uploadDiagnosis(String authToken, MultipartFile[] diagnosis);

	void getPrescription(String authToken, long prescriptionId, HttpServletResponse httpResponse);

	void getDiagnosis(String authToken, long diagnosisId, HttpServletResponse httpResponse);

	DeletePrescriptionResponse deletePrescription(String authToken, List<Long> prescriptionIds);

	DeleteDiagnosisResponse deleteDiagnosis(String authToken, List<Long> diagnosisIds);
}
