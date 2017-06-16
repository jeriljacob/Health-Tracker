package com.psquickit.managerImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.psquickit.common.HandledException;
import com.psquickit.dao.DiagnosisReportFileDAO;
import com.psquickit.dao.HealthRecordDAO;
import com.psquickit.dao.PrescriptionFileDAO;
import com.psquickit.dao.SubTestNameValueDAO;
import com.psquickit.dao.TestReportFileDAO;
import com.psquickit.dao.UserDAO;
import com.psquickit.dao.UserDiagnosisReportDAO;
import com.psquickit.dao.UserPrescriptionDAO;
import com.psquickit.dao.UserTestNameValueReportDAO;
import com.psquickit.dto.DiagnosisReportFileDTO;
import com.psquickit.dto.FileStoreDTO;
import com.psquickit.dto.HealthRecordDTO;
import com.psquickit.dto.PrescriptionFileDTO;
import com.psquickit.dto.SubTestNameValueDTO;
import com.psquickit.dto.TestReportFileDTO;
import com.psquickit.dto.UserDiagnosisReportDTO;
import com.psquickit.dto.UserPrescriptionDTO;
import com.psquickit.dto.UserTestNameValueReportDTO;
import com.psquickit.manager.AuthenticationManager;
import com.psquickit.manager.FileStoreManager;
import com.psquickit.manager.HealthRecordManager;
import com.psquickit.pojo.health.record.AddTestNameValue;
import com.psquickit.pojo.health.record.DeleteDiagnosisResponse;
import com.psquickit.pojo.health.record.DeletePrescriptionResponse;
import com.psquickit.pojo.health.record.DeleteTestResponse;
import com.psquickit.pojo.health.record.GetHealthRecordResponse;
import com.psquickit.pojo.health.record.GetTestNameValueReportResponse;
import com.psquickit.pojo.health.record.TestNameValue;
import com.psquickit.pojo.health.record.TestNameValueById;
import com.psquickit.pojo.health.record.TestNameValueReport;
import com.psquickit.pojo.health.record.TestReportFile;
import com.psquickit.pojo.health.record.UpdateTestNameValue;
import com.psquickit.pojo.health.record.UploadDiagnosisResponse;
import com.psquickit.pojo.health.record.UploadPrescriptionResponse;
import com.psquickit.util.ServiceUtils;

@Service
public class HealthRecordManagerImpl implements HealthRecordManager {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	HealthRecordDAO healthRecordDAO;
	
	@Autowired
	UserTestNameValueReportDAO userTestNameValueReportDAO;
	
	@Autowired
	SubTestNameValueDAO subTestNameValueDAO;
	
	@Autowired
	TestReportFileDAO testReportFileDAO;
	
	@Autowired
	FileStoreManager fileStoreManager;
	
	@Autowired
	PrescriptionFileDAO prescriptionFileDAO;
	
	@Autowired
	UserPrescriptionDAO userPrescriptionDAO;
	
	@Autowired
	DiagnosisReportFileDAO diagnosisReportFileDAO;
	
	@Autowired
	UserDiagnosisReportDAO userDiagnosisReportDAO;
	
	
	@Override
	public GetHealthRecordResponse getHealthRecord(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetTestNameValueReportResponse addTestNameValue(String authToken, AddTestNameValue request) throws Exception {
		
		long userId = authManager.getUserId(authToken);
		
		
		HealthRecordDTO hdto = null;
		if (request.getHealthRecordId() == null) {
			hdto = new HealthRecordDTO();
			hdto.setUser(userDAO.findOne(userId));
			hdto.setRecordDate(new Timestamp(request.getHealthRecordDate().getTime()));
		} else {
			hdto = getHealthRecordDTO(Long.parseLong(request.getHealthRecordId()), userId);
		}
				
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
		
		healthRecordDAO.save(hdto);
		
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		return ServiceUtils.setResponse(response, true, "Add test name value");
	}

	private HealthRecordDTO getHealthRecordDTO(long healthRecordId, long userId) throws Exception {
		HealthRecordDTO hdto = healthRecordDAO.findOne(healthRecordId);
		if (hdto == null) {
			throw new HandledException("INVALID_HEALTH_RECORD_ID", "Invalid health record ID: " + healthRecordId);
		}
		//TODO: Here we need to add the functionality for those doctors with whom
		//the user has shared his health record to add things.
		if (hdto.getUser().getId() != userId) {
			throw new HandledException("NOT_SAME_USER", "User who created the health record & the user"
					+ "who is adding test reports are different.");
		}
		return hdto;
	}
	
	@Override
	public GetTestNameValueReportResponse updateTestNameValue(String authToken, UpdateTestNameValue request) throws Exception {
		
		long userId = authManager.getUserId(authToken);
		HealthRecordDTO hdto = getHealthRecordDTO(Long.parseLong(request.getHealthRecordId()), userId);
		
		List<TestNameValueById> testNameValuesById = request.getTestNameValueById();
		for (TestNameValueById testNameValueById: testNameValuesById) {
			UserTestNameValueReportDTO tdto = userTestNameValueReportDAO.findOne(Long.parseLong(testNameValueById.getId()));
			if (tdto.getHealthrecord().getId() != hdto.getId()) {
				throw new HandledException("INVALID_TEST_ID", "Test with id: " + testNameValueById.getId() + " does not "
						+ "belong to health record with id: " + request.getHealthRecordId());
			}
			tdto.setTestName(testNameValueById.getTestName());
			tdto.setTestValue(testNameValueById.getTestValue());
			tdto.setTestValuesRange(testNameValueById.getTestRange());
			tdto.setUnit(testNameValueById.getTestUnit());
			userTestNameValueReportDAO.save(tdto);
			
			List<TestNameValueById> subTestNameValuesById = testNameValueById.getSubTestNameValueById();
			for (TestNameValueById subTestNameValueById: subTestNameValuesById) {
				SubTestNameValueDTO stdto = subTestNameValueDAO.findOne(Long.parseLong(subTestNameValueById.getId()));
				if (stdto.getUsertestnamevaluereport().getId() != tdto.getId()) {
					throw new HandledException("INVALID_SUBTEST_ID", "Subtest with id: " + subTestNameValueById.getId() + " does not "
							+ "belong to test with id: " + testNameValueById.getId());
				}
				stdto.setTestName(subTestNameValueById.getTestName());
				stdto.setTestValue(subTestNameValueById.getTestValue());
				stdto.setTestValuesRange(subTestNameValueById.getTestRange());
				stdto.setUnit(subTestNameValueById.getTestUnit());
				stdto.setUsertestnamevaluereport(tdto);
				subTestNameValueDAO.save(stdto);
			}
		}
		
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		return ServiceUtils.setResponse(response, true, "Update test name value");
	}

	@Override
	public GetTestNameValueReportResponse getTestNameValueReport(String authToken, long healthRecordId) throws Exception {
		long userId = authManager.getUserId(authToken);
		List<UserTestNameValueReportDTO> tdtos = userTestNameValueReportDAO.listUserTestNameValueReportByHealthRecordId(healthRecordId, userId);
		if (!tdtos.isEmpty()) {
			if (tdtos.get(0).getHealthrecord().getUser().getId() != userId) {
				throw new HandledException("NOT_SAME_USER", "User does not have right to view this health record.");
			}
		}
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		response.getTestNameValueReport().addAll(populateTestNameValueReport(tdtos));
		return ServiceUtils.setResponse(response, true, "Get tests");
	}
	
	private List<TestNameValueReport> populateTestNameValueReport(List<UserTestNameValueReportDTO> tdtos) {
		List<TestNameValueReport> tests = Lists.newArrayList();
		for (UserTestNameValueReportDTO tdto: tdtos) {
			TestNameValueReport test = new TestNameValueReport();
			test.setId(Long.toString(tdto.getId()));
			test.setTestName(tdto.getTestName());
			test.setTestRange(tdto.getTestValuesRange());
			test.setTestUnit(tdto.getUnit());
			
			List<TestReportFile> reportFiles = Lists.newArrayList();
			List<TestReportFileDTO> trfdtos = testReportFileDAO.listTestReportFileByUserTestNameValueReportId(tdto.getId());
			for (TestReportFileDTO trfdto: trfdtos) {
				TestReportFile reportFile = new TestReportFile();
				reportFile.setId(Long.toString(trfdto.getId()));
				reportFile.setFileStoreId(Long.toString(trfdto.getFilestore().getId()));
				reportFiles.add(reportFile);
			}
			test.getTestReportFile().addAll(reportFiles);
			
			List<SubTestNameValueDTO> stdtos = subTestNameValueDAO.listSubTestNameValueByUserTestNameValueReportId(tdto.getId());
			List<TestNameValueById> subtests = Lists.newArrayList();
			for (SubTestNameValueDTO stdto: stdtos) {
				TestNameValueById subtest = new TestNameValueById();
				subtest.setId(Long.toString(stdto.getId()));
				subtest.setTestName(stdto.getTestName());
				subtest.setTestRange(stdto.getTestValuesRange());
				subtest.setTestUnit(stdto.getUnit());
				subtests.add(subtest);
			}
			test.getSubTestNameValueById().addAll(subtests);
			
			tests.add(test);
		}
		
		return tests;
	}

	@Override
	public DeleteTestResponse deleteTest(String authToken, List<Long> ids) throws Exception {
		DeleteTestResponse response = new DeleteTestResponse();
		return ServiceUtils.setResponse(response, true, "Delete tests");
	}

	@Override
	public GetTestNameValueReportResponse addTestNameReport(String authToken, String healthRecordId,
			Date healthRecordDate, String testName, MultipartFile[] testReports) throws Exception {
		
		long userId = authManager.getUserId(authToken);
		HealthRecordDTO hdto = null;
		if (healthRecordId == null) {
			hdto = new HealthRecordDTO();
			hdto.setUser(userDAO.getOne(userId));
			hdto.setRecordDate(new Timestamp(healthRecordDate.getTime()));
		} else {
			hdto = getHealthRecordDTO(Long.parseLong(healthRecordId), userId);
		}

		UserTestNameValueReportDTO tdto = new UserTestNameValueReportDTO();
		tdto.setTestName(testName);
		tdto.setHealthrecord(hdto);
		userTestNameValueReportDAO.save(tdto);

		List<TestReportFileDTO> trdtos = Lists.newArrayList();
		for (MultipartFile testReport: testReports) {
			TestReportFileDTO trdto = new TestReportFileDTO();
			FileStoreDTO filestore = fileStoreManager.uploadFile(testReport.getInputStream(), testReport.getContentType(), testReport.getOriginalFilename());
			trdto.setFilestore(filestore);
			trdto.setUsertestnamevaluereport(tdto);
			trdtos.add(trdto);
		}
		testReportFileDAO.save(trdtos);
		
		GetTestNameValueReportResponse response = new GetTestNameValueReportResponse();
		return ServiceUtils.setResponse(response, true, "Add a test report");
	}

	@Override
	public void getTestNameReport(String authToken, long testReportId, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UploadPrescriptionResponse uploadPrescription(String authToken, String healthRecordId,
			Date healthRecordDate, MultipartFile[] prescriptions) throws Exception {
		long userId = authManager.getUserId(authToken);
		HealthRecordDTO hdto = null;
		if (healthRecordId == null) {
			hdto = new HealthRecordDTO();
			hdto.setUser(userDAO.getOne(userId));
			hdto.setRecordDate(new Timestamp(healthRecordDate.getTime()));
		} else {
			hdto = getHealthRecordDTO(Long.parseLong(healthRecordId), userId);
		}
		
		UserPrescriptionDTO updto = new UserPrescriptionDTO();
		updto.setHealthrecord(hdto);
		userPrescriptionDAO.save(updto);
		
		List<PrescriptionFileDTO> pdtos = Lists.newArrayList();
		for (MultipartFile prescription: prescriptions) {
			PrescriptionFileDTO pdto = new PrescriptionFileDTO();
			FileStoreDTO filestore = fileStoreManager.uploadFile(prescription.getInputStream(), prescription.getContentType(), prescription.getOriginalFilename());
			pdto.setFilestore(filestore);
			pdto.setUserprescription(updto);
			pdtos.add(pdto);
		}
		prescriptionFileDAO.save(pdtos);
		
		UploadPrescriptionResponse response = new UploadPrescriptionResponse();
		return ServiceUtils.setResponse(response, true, "Upload prescription");
	}

	@Override
	public UploadDiagnosisResponse uploadDiagnosis(String authToken, String healthRecordId,
			Date healthRecordDate, String diagnosisName, MultipartFile[] diagnosises) throws Exception {
		long userId = authManager.getUserId(authToken);
		HealthRecordDTO hdto = null;
		if (healthRecordId == null) {
			hdto = new HealthRecordDTO();
			hdto.setUser(userDAO.getOne(userId));
			hdto.setRecordDate(new Timestamp(healthRecordDate.getTime()));
		} else {
			hdto = getHealthRecordDTO(Long.parseLong(healthRecordId), userId);
		}
		
		UserDiagnosisReportDTO udrdto = new UserDiagnosisReportDTO();
		udrdto.setHealthrecord(hdto);
		userDiagnosisReportDAO.save(udrdto);
		
		List<DiagnosisReportFileDTO> drdtos = Lists.newArrayList();
		for (MultipartFile diagnosis: diagnosises) {
			DiagnosisReportFileDTO drdto = new DiagnosisReportFileDTO();
			FileStoreDTO filestore = fileStoreManager.uploadFile(diagnosis.getInputStream(), diagnosis.getContentType(), diagnosis.getOriginalFilename());
			drdto.setFilestore(filestore);
			drdto.setUserdiagnosisreport(udrdto);
			drdtos.add(drdto);
		}
		diagnosisReportFileDAO.save(drdtos);
		
		UploadDiagnosisResponse response = new UploadDiagnosisResponse();
		return ServiceUtils.setResponse(response, true, "Upload diagnosis");
	}

	@Override
	public void getPrescription(String authToken, long prescriptionId, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDiagnosis(String authToken, long diagnosisId, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DeletePrescriptionResponse deletePrescription(String authToken, List<Long> prescriptionIds) throws Exception {
		DeletePrescriptionResponse response = new DeletePrescriptionResponse();
		return ServiceUtils.setResponse(response, true, "Delete prescription");
	}

	@Override
	public DeleteDiagnosisResponse deleteDiagnosis(String authToken, List<Long> diagnosisIds) throws Exception {
		DeleteDiagnosisResponse response = new DeleteDiagnosisResponse();
		return ServiceUtils.setResponse(response, true, "Delete diagnosis");
	}
}
