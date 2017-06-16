package com.psquickit.managerImpl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.psquickit.dao.SubTestNameValueDAO;
import com.psquickit.dao.UserTestNameValueReportDAO;
import com.psquickit.dto.SubTestNameValueDTO;
import com.psquickit.dto.UserTestNameValueReportDTO;
import com.psquickit.manager.HealthRecordManager;
import com.psquickit.pojo.health.record.AddTestNameValue;
import com.psquickit.pojo.health.record.DeleteDiagnosisResponse;
import com.psquickit.pojo.health.record.DeletePrescriptionResponse;
import com.psquickit.pojo.health.record.DeleteTestResponse;
import com.psquickit.pojo.health.record.GetHealthRecordResponse;
import com.psquickit.pojo.health.record.GetTestNameValueReportResponse;
import com.psquickit.pojo.health.record.TestNameValue;
import com.psquickit.pojo.health.record.UpdateTestNameValue;
import com.psquickit.pojo.health.record.UploadDiagnosisResponse;
import com.psquickit.pojo.health.record.UploadPrescriptionResponse;
import com.psquickit.util.ServiceUtils;

@Service
public class HealthRecordManagerImpl implements HealthRecordManager {

	@Autowired
	UserTestNameValueReportDAO userTestNameValueReportDAO;
	
	@Autowired
	SubTestNameValueDAO subTestNameValueDAO;
	
	@Override
	public GetHealthRecordResponse getHealthRecord(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetTestNameValueReportResponse addTestNameValue(String authToken, AddTestNameValue request) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		List<TestNameValue> testNameValues = request.getTestNameValue();
		for (TestNameValue testNameValue: testNameValues) {
			UserTestNameValueReportDTO tdto = new UserTestNameValueReportDTO();
			tdto.setTestName(testNameValue.getTestName());
			tdto.setTestValue(testNameValue.getTestValue());
			tdto.setTestValuesRange(testNameValue.getTestRange());
			tdto.setUnit(testNameValue.getTestUnit());
			userTestNameValueReportDAO.save(tdto);
			
			List<TestNameValue> subTestNameValues = testNameValue.getSubTestNameValue();
			for (TestNameValue subTestNameValue: subTestNameValues) {
				SubTestNameValueDTO stdto = new SubTestNameValueDTO();
				stdto.setTestName(subTestNameValue.getTestName());
				stdto.setTestValue(subTestNameValue.getTestValue());
				stdto.setTestValuesRange(subTestNameValue.getTestRange());
				stdto.setUnit(subTestNameValue.getTestUnit());
				stdto.setUsertestnamevaluereport(tdto);
				subTestNameValueDAO.save(stdto);
			}
		}
		return ServiceUtils.setResponse(response, true, "Add test name value");
	}

	@Override
	public GetTestNameValueReportResponse updateTestNameValue(String authToken, UpdateTestNameValue request) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		return ServiceUtils.setResponse(response, true, "Update test name value");
	}

	@Override
	public GetTestNameValueReportResponse getTestNameValueReport(String authToken) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		return ServiceUtils.setResponse(response, true, "Get tests");
	}

	@Override
	public DeleteTestResponse deleteTest(String authToken, List<Long> ids) {
		DeleteTestResponse response = new DeleteTestResponse();
		return ServiceUtils.setResponse(response, true, "Delete tests");
	}

	@Override
	public GetTestNameValueReportResponse addTestNameReport(String authToken, String testName,
			MultipartFile testReport) {
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		return ServiceUtils.setResponse(response, true, "Add a test report");
	}

	@Override
	public void getTestNameReport(String authToken, long testReportId, HttpServletResponse httpResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UploadPrescriptionResponse uploadPrescription(String authToken, MultipartFile[] prescription) {
		UploadPrescriptionResponse response = new UploadPrescriptionResponse();
		return ServiceUtils.setResponse(response, true, "Upload prescription");
	}

	@Override
	public UploadDiagnosisResponse uploadDiagnosis(String authToken, MultipartFile[] diagnosis) {
		UploadDiagnosisResponse response = new UploadDiagnosisResponse();
		return ServiceUtils.setResponse(response, true, "Upload diagnosis");
	}

	@Override
	public void getPrescription(String authToken, long prescriptionId, HttpServletResponse httpResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDiagnosis(String authToken, long diagnosisId, HttpServletResponse httpResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DeletePrescriptionResponse deletePrescription(String authToken, List<Long> prescriptionIds) {
		DeletePrescriptionResponse response = new DeletePrescriptionResponse();
		return ServiceUtils.setResponse(response, true, "Delete prescription");
	}

	@Override
	public DeleteDiagnosisResponse deleteDiagnosis(String authToken, List<Long> diagnosisIds) {
		DeleteDiagnosisResponse response = new DeleteDiagnosisResponse();
		return ServiceUtils.setResponse(response, true, "Delete diagnosis");
	}
}
