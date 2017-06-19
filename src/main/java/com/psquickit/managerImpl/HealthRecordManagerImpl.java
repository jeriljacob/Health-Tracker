package com.psquickit.managerImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

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
import com.psquickit.pojo.health.record.Diagnosis;
import com.psquickit.pojo.health.record.DiagnosisFile;
import com.psquickit.pojo.health.record.GetHealthRecordResponse;
import com.psquickit.pojo.health.record.GetTestNameValueReportResponse;
import com.psquickit.pojo.health.record.HealthRecord;
import com.psquickit.pojo.health.record.ListHealthRecordResponse;
import com.psquickit.pojo.health.record.Prescription;
import com.psquickit.pojo.health.record.PrescriptionFile;
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
	@Transactional
	public ListHealthRecordResponse listHealthRecord(String authToken) throws Exception {
		long userId = authManager.getUserId(authToken);
		List<HealthRecordDTO> hrdtos = healthRecordDAO.listHealthRecordByUserId(userId);
		List<HealthRecord> hrs = Lists.newArrayList();
		for (HealthRecordDTO hrdto: hrdtos) {
			HealthRecord hr = populateHealthRecord(hrdto.getId(), userId);
			hrs.add(hr);
		}
		
		ListHealthRecordResponse response = new ListHealthRecordResponse();
		response.getHealthRecord().addAll(hrs);
		return ServiceUtils.setResponse(response, true, "List health record");
	}
	
	@Override
	@Transactional
	public GetHealthRecordResponse getHealthRecord(String authToken, long healthRecordId) throws Exception {
		long userId = authManager.getUserId(authToken);
		GetHealthRecordResponse response = new GetHealthRecordResponse();
		response.setHealthRecord(populateHealthRecord(healthRecordId, userId));
		return ServiceUtils.setResponse(response, true, "Get health record");
	}
	
	private HealthRecord populateHealthRecord(long healthRecordId, long userId) {
		HealthRecordDTO hdto = healthRecordDAO.getByHealthRecordIdAndUserId(healthRecordId, userId);
		
		List<UserTestNameValueReportDTO> tdtos = userTestNameValueReportDAO.listUserTestNameValueReportByHealthRecordIdAndUserId(healthRecordId, userId);
		List<TestNameValueReport> tnvrs = populateTestNameValueReport(tdtos);
		
		List<UserPrescriptionDTO> updtos = userPrescriptionDAO.listUserPrescriptionByHealthRecordIdAndUserId(healthRecordId, userId);
		List<Prescription> ps = populatePrescription(updtos);
		
		List<UserDiagnosisReportDTO> udrdtos = userDiagnosisReportDAO.listUserDiagnosisReportByHealthRecordIdAndUserId(healthRecordId, userId);
		List<Diagnosis> ds = populateDiagnosis(udrdtos);
		
		HealthRecord hr = new HealthRecord();
		hr.getTestNameValueReport().addAll(tnvrs);
		hr.getPrescription().addAll(ps);
		hr.getDiagnosis().addAll(ds);
		hr.setHealthRecordDate(hdto.getRecordDate());
		
		return hr;
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
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
			tdto.setHealthrecord(hdto);
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
	@Transactional(rollbackOn=Exception.class)
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
	@Transactional
	public GetTestNameValueReportResponse getTestNameValueReport(String authToken, long healthRecordId) throws Exception {
		long userId = authManager.getUserId(authToken);
		List<UserTestNameValueReportDTO> tdtos = userTestNameValueReportDAO.listUserTestNameValueReportByHealthRecordIdAndUserId(healthRecordId, userId);
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
	@Transactional(rollbackOn=Exception.class)
	public DeleteTestResponse deleteTest(String authToken, List<Long> ids) throws Exception {
		DeleteTestResponse response = new DeleteTestResponse();
		//TODO: to implement
		return ServiceUtils.setResponse(response, true, "Delete tests");
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
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
	public void getTestNameReport(String authToken, long testReportFileId, HttpServletResponse httpResponse) throws Exception {
		TestReportFileDTO trf = testReportFileDAO.findOne(testReportFileId);
		getFileContent(httpResponse, trf.getFilestore());		
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
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
		response.setPrescriptionId(updto.getId().toString());
		return ServiceUtils.setResponse(response, true, "Upload prescription");
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
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
		udrdto.setDiagnosisName(diagnosisName);
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
		response.setDiagnosisId(udrdto.getId().toString());
		return ServiceUtils.setResponse(response, true, "Upload diagnosis");
	}

	@Override
	@Transactional
	public void getPrescription(String authToken, long prescriptionFileId, HttpServletResponse httpResponse) throws Exception {
		PrescriptionFileDTO pf = prescriptionFileDAO.findOne(prescriptionFileId);
		getFileContent(httpResponse, pf.getFilestore());		
	}
	
	private List<Prescription> populatePrescription(List<UserPrescriptionDTO> updtos) {
		List<Prescription> ps = Lists.newArrayList();
		for (UserPrescriptionDTO updto: updtos) {
			Prescription p = new Prescription();
			p.setId(Long.toString(updto.getId()));
			
			List<PrescriptionFile> pfs = Lists.newArrayList();
			List<PrescriptionFileDTO> pfdtos = prescriptionFileDAO.listPrescriptionFileByPrescriptionId(updto.getId());
			for (PrescriptionFileDTO pfdto: pfdtos) {
				PrescriptionFile pf = new PrescriptionFile();
				pf.setId(Long.toString(pfdto.getId()));
				pf.setFileStoreId(Long.toString(pfdto.getFilestore().getId()));
				pfs.add(pf);
			}
			p.getPrescriptionFile().addAll(pfs);
			ps.add(p);
		}
		return ps;
	}

	@Override
	@Transactional
	public void getDiagnosis(String authToken, long diagnosisReportFileId, HttpServletResponse httpResponse) throws Exception {
		DiagnosisReportFileDTO drf = diagnosisReportFileDAO.findOne(diagnosisReportFileId);
		getFileContent(httpResponse, drf.getFilestore());		
	}

	private void getFileContent(HttpServletResponse httpResponse, FileStoreDTO fsdto) throws IOException, Exception {
		httpResponse.setContentType(fsdto.getDocumentType());
		httpResponse.setHeader("Content-Disposition", "attachment;filename=" + fsdto.getFileName());
        
		try(OutputStream outputStream = httpResponse.getOutputStream()){
			fileStoreManager.retrieveFile(fsdto).copyTo(outputStream);
		}
	}

	private List<Diagnosis> populateDiagnosis(List<UserDiagnosisReportDTO> udrdtos) {
		List<Diagnosis> ds = Lists.newArrayList();
		for (UserDiagnosisReportDTO udrdto: udrdtos) {
			Diagnosis d = new Diagnosis();
			d.setId(Long.toString(udrdto.getId()));
			
			List<DiagnosisFile> dfs = Lists.newArrayList();
			List<DiagnosisReportFileDTO> pfdtos = diagnosisReportFileDAO.listDiagnosisReportFileByDiagnosisId(udrdto.getId());
			for (DiagnosisReportFileDTO pfdto: pfdtos) {
				DiagnosisFile df = new DiagnosisFile();
				df.setId(Long.toString(pfdto.getId()));
				df.setFileStoreId(Long.toString(pfdto.getFilestore().getId()));
				dfs.add(df);
			}
			d.getDiagnosisFile().addAll(dfs);
			ds.add(d);
		}
		return ds;
	}
	
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DeletePrescriptionResponse deletePrescription(String authToken, List<Long> prescriptionIds) throws Exception {
		DeletePrescriptionResponse response = new DeletePrescriptionResponse();
		//TODO: to implement
		return ServiceUtils.setResponse(response, true, "Delete prescription");
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public DeleteDiagnosisResponse deleteDiagnosis(String authToken, List<Long> diagnosisIds) throws Exception {
		DeleteDiagnosisResponse response = new DeleteDiagnosisResponse();
		//TODO: to implement
		return ServiceUtils.setResponse(response, true, "Delete diagnosis");
	}
}
