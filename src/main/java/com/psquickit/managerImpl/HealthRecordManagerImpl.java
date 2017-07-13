package com.psquickit.managerImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.psquickit.common.HandledException;
import com.psquickit.dao.DiagnosisReportFileDAO;
import com.psquickit.dao.HealthRecordDAO;
import com.psquickit.dao.PrescriptionFileDAO;
import com.psquickit.dao.PrescriptionRxDAO;
import com.psquickit.dao.SharedHealthRecordDAO;
import com.psquickit.dao.SharedUserRecordDAO;
import com.psquickit.dao.SubTestNameValueDAO;
import com.psquickit.dao.TestReportFileDAO;
import com.psquickit.dao.UserDAO;
import com.psquickit.dao.UserDiagnosisReportDAO;
import com.psquickit.dao.UserPrescriptionDAO;
import com.psquickit.dao.UserPrescriptionNameValueDAO;
import com.psquickit.dao.UserTestNameValueReportDAO;
import com.psquickit.dto.DiagnosisReportFileDTO;
import com.psquickit.dto.FileStoreDTO;
import com.psquickit.dto.HealthRecordDTO;
import com.psquickit.dto.PresciptionRxDTO;
import com.psquickit.dto.PrescriptionFileDTO;
import com.psquickit.dto.SharedHealthRecordDTO;
import com.psquickit.dto.SharedUserRecordDTO;
import com.psquickit.dto.SubTestNameValueDTO;
import com.psquickit.dto.TestReportFileDTO;
import com.psquickit.dto.UserDTO;
import com.psquickit.dto.UserDiagnosisReportDTO;
import com.psquickit.dto.UserPrescriptionDTO;
import com.psquickit.dto.UserPrescriptionNameValueDTO;
import com.psquickit.dto.UserTestNameValueReportDTO;
import com.psquickit.manager.AuthenticationManager;
import com.psquickit.manager.FileStoreManager;
import com.psquickit.manager.HealthRecordManager;
import com.psquickit.pojo.health.record.AddPrescriptionNameValue;
import com.psquickit.pojo.health.record.AddShareHealthRecord;
import com.psquickit.pojo.health.record.AddShareHealthRecordResponse;
import com.psquickit.pojo.health.record.AddTestNameValue;
import com.psquickit.pojo.health.record.DeleteDiagnosisResponse;
import com.psquickit.pojo.health.record.DeletePrescriptionResponse;
import com.psquickit.pojo.health.record.DeleteTestResponse;
import com.psquickit.pojo.health.record.Diagnosis;
import com.psquickit.pojo.health.record.DiagnosisFile;
import com.psquickit.pojo.health.record.GetHealthRecordResponse;
import com.psquickit.pojo.health.record.GetPrescriptionNameValueResponse;
import com.psquickit.pojo.health.record.GetShareHealthRecordResponse;
import com.psquickit.pojo.health.record.GetTestNameValueReportResponse;
import com.psquickit.pojo.health.record.HealthRecord;
import com.psquickit.pojo.health.record.ListHealthRecordResponse;
import com.psquickit.pojo.health.record.ListShareHealthRecordResponse;
import com.psquickit.pojo.health.record.Prescription;
import com.psquickit.pojo.health.record.PrescriptionFile;
import com.psquickit.pojo.health.record.PrescriptionNameValue;
import com.psquickit.pojo.health.record.PrescriptionRx;
import com.psquickit.pojo.health.record.ShareRecordAttrs;
import com.psquickit.pojo.health.record.TestNameValue;
import com.psquickit.pojo.health.record.TestNameValueById;
import com.psquickit.pojo.health.record.TestNameValueReport;
import com.psquickit.pojo.health.record.TestReportFile;
import com.psquickit.pojo.health.record.UpdateShareHealthRecord;
import com.psquickit.pojo.health.record.UpdateShareHealthRecordResponse;
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
	
	@Autowired
	SharedUserRecordDAO sharedUserRecordDAO;
	
	@Autowired
	SharedHealthRecordDAO sharedHealthRecordDAO;
	
	@Autowired
	UserPrescriptionNameValueDAO userPrescriptionNameValueDAO;
	
	@Autowired
	PrescriptionRxDAO prescriptionRxDAO;
	
	
	@Override
	@Transactional
	public ListHealthRecordResponse listHealthRecord(String authToken) throws Exception {
		//used by individual user
		long userId = authManager.getUserId(authToken);
		List<HealthRecordDTO> hrdtos = healthRecordDAO.listHealthRecordByUserId(userId);
		return listHealthRecord(hrdtos);
	}

	@Override
	@Transactional
	public ListHealthRecordResponse listHealthRecord(String authToken, List<Long> healthRecordIdsShared) throws Exception {
		//used by doctor user
		long userId = authManager.getUserId(authToken);
		List<HealthRecordDTO> hrdtos = sharedHealthRecordDAO.listSharedHealthRecordsIn(healthRecordIdsShared, userId);
		return listHealthRecord(hrdtos);
	}
	
	@Override
	@Transactional
	public ListHealthRecordResponse listHealthRecordSharedToMeByAUser(String authToken, long sharedByUserId) throws Exception {
		//used by doctor user
		long userId = authManager.getUserId(authToken);
		List<HealthRecordDTO> hrdtos = sharedHealthRecordDAO.listSharedHealthRecords(sharedByUserId, userId);
		return listHealthRecord(hrdtos);
	}
	
	private ListHealthRecordResponse listHealthRecord(List<HealthRecordDTO> hrdtos) {
		List<HealthRecord> hrs = Lists.newArrayList();
		for (HealthRecordDTO hrdto: hrdtos) {
			HealthRecord hr = populateHealthRecord(hrdto);
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
		//TODO: Not sure if this api will be used as we already have the list apis. at present, we are not
		//giving this api for the doctor user
		HealthRecordDTO hdto = healthRecordDAO.getByHealthRecordIdAndUserId(healthRecordId, userId);
		response.setHealthRecord(populateHealthRecord(hdto));
		return ServiceUtils.setResponse(response, true, "Get health record");
	}
	
	private HealthRecord populateHealthRecord(HealthRecordDTO hdto) {
		List<UserTestNameValueReportDTO> tdtos = hdto.getUsertestnamevaluereports();
		List<TestNameValueReport> tnvrs = populateTestNameValueReport(tdtos);
		
		List<UserPrescriptionDTO> updtos = hdto.getUserprescriptions();
		List<Prescription> ps = populatePrescription(updtos);
		
		List<UserDiagnosisReportDTO> udrdtos = hdto.getUserdiagnosisreports();
		List<Diagnosis> ds = populateDiagnosis(udrdtos);
		
		List<UserPrescriptionNameValueDTO> upnvdtos = hdto.getUserprescriptionnamevalues();
		
		List<PrescriptionNameValue> pnvs=populatePrescriptionNameValue(upnvdtos);
		HealthRecord hr = new HealthRecord();
		hr.getTestNameValueReport().addAll(tnvrs);
		hr.getPrescription().addAll(ps);
		hr.getDiagnosis().addAll(ds);
		hr.getPrescriptionNameValue().addAll(pnvs);
	
		hr.setHealthRecordDate(hdto.getRecordDate());
		hr.setHealthRecordId(Long.toString(hdto.getId()));  
		
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
			hdto = healthRecordDAO.save(hdto);
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
		
		GetTestNameValueReportResponse response = getTestNameValueReport(authToken, hdto.getId());
		return ServiceUtils.setResponse(response, true, "Add test name value");
	}

	private HealthRecordDTO getHealthRecordDTO(long healthRecordId, long userId) throws Exception {
		HealthRecordDTO hdto = healthRecordDAO.findOne(healthRecordId);
		if (hdto == null) {
			throw new HandledException("INVALID_HEALTH_RECORD_ID", "Invalid health record ID: " + healthRecordId);
		}
		validateHealthRecordAccess(hdto, userId);
		return hdto;
	}

	private void validateHealthRecordAccess(long healthRecordId, long userId) throws Exception {
		HealthRecordDTO hdto = healthRecordDAO.findOne(healthRecordId);
		if (hdto == null) {
			throw new HandledException("INVALID_HEALTH_RECORD_ID", "Invalid health record ID: " + healthRecordId);
		}
		validateHealthRecordAccess(hdto, userId);
	}
	
	private void validateHealthRecordAccess(HealthRecordDTO hdto, long userId) throws Exception {
		if (hdto.getUser().getId() != userId) {
			//check if this health record was shared with this user id
			List<SharedHealthRecordDTO> shrdtos = sharedHealthRecordDAO.listSharedHealthRecordByHealthRecordId(hdto.getId());
			for (SharedHealthRecordDTO shrdto: shrdtos) {
				if (shrdto != null && (shrdto.getShareduserrecord().getSharedTo().getId() == userId)) {
					//implies user has shared this record
					return;
				}
			}			
			throw new HandledException("NOT_SAME_USER", "User " + userId + " cannot access the health record.");
		}
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
		
		GetTestNameValueReportResponse response = getTestNameValueReport(authToken, hdto.getId());
		return ServiceUtils.setResponse(response, true, "Update test name value");
	}

	@Override
	@Transactional
	public GetTestNameValueReportResponse getTestNameValueReport(String authToken, long healthRecordId) throws Exception {
		long userId = authManager.getUserId(authToken);
		validateHealthRecordAccess(healthRecordId, userId);
		List<UserTestNameValueReportDTO> tdtos = userTestNameValueReportDAO.listUserTestNameValueReportByHealthRecordId(healthRecordId);
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
			test.setTestValue(tdto.getTestValue());
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
				subtest.setTestValue(stdto.getTestValue());
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
			ZonedDateTime healthRecordDate, String forUserId, String testName, MultipartFile[] testReports) throws Exception {
		
		long userId = authManager.getUserId(authToken);
		HealthRecordDTO hdto = null;
		if (healthRecordId == null) {
			hdto = generateHealthRecordDTO(healthRecordDate, userId, forUserId);
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
		
		GetTestNameValueReportResponse response = getTestNameValueReport(authToken, hdto.getId());
		return ServiceUtils.setResponse(response, true, "Add a test report");
	}

	private HealthRecordDTO generateHealthRecordDTO(ZonedDateTime healthRecordDate, long userId, String forUserIdStr) throws HandledException {
		HealthRecordDTO hdto = new HealthRecordDTO();
		
		SharedUserRecordDTO surdto = null;
		if (forUserIdStr != null) {
			//this means someone else (probably a HSP) is adding a new health record on behalf of the individual
			//in this case, we need to check if the individual has ever shared anything with the HSP. If yes,
			//then continue, else, don't
			long forUserId = Long.parseLong(forUserIdStr);
			surdto = sharedUserRecordDAO.getSharedUserRecord(forUserId, userId);
			boolean hasAccess = false;
			if (surdto != null) {
				if (!surdto.getSharedhealthrecords().isEmpty()) {
					hasAccess = true;
				}
			} 
			if (!hasAccess) {
				throw new HandledException("INVALID_ACCESS", "No health records are shared by the user [" + forUserIdStr + "] to user [" + userId + "]. Cannot proceed with the operation of creating new health record");
			}
			hdto.setUser(userDAO.getOne(forUserId));
		} else {
			hdto.setUser(userDAO.getOne(userId));
		}
		hdto.setRecordDate(new Timestamp(healthRecordDate.toInstant().toEpochMilli()));
		hdto = healthRecordDAO.save(hdto);
		
		if (forUserIdStr != null && surdto != null) {
			//if the HSP is adding the health record, then the health record should
			//by default be shared with HSP
			SharedHealthRecordDTO shrdto = new SharedHealthRecordDTO();
			shrdto.setHealthrecord(hdto);
			shrdto.setShareduserrecord(surdto);
			shrdto = sharedHealthRecordDAO.save(shrdto);
		}
		return hdto;
	}
	
	

	@Override
	public void getTestNameReport(String authToken, long testReportFileId, HttpServletResponse httpResponse) throws Exception {
		long userId = authManager.getUserId(authToken);
		TestReportFileDTO trf = testReportFileDAO.findOne(testReportFileId);
		validateHealthRecordAccess(trf.getUsertestnamevaluereport().getHealthrecord(), userId);
		getFileContent(httpResponse, trf.getFilestore());		
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public UploadPrescriptionResponse uploadPrescription(String authToken, String healthRecordId,
			ZonedDateTime healthRecordDate, String forUserId, MultipartFile[] prescriptions) throws Exception {
		long userId = authManager.getUserId(authToken);
		HealthRecordDTO hdto = null;
		if (healthRecordId == null) {
			hdto = generateHealthRecordDTO(healthRecordDate, userId, forUserId);
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
			ZonedDateTime healthRecordDate, String forUserId, String diagnosisName, MultipartFile[] diagnosises) throws Exception {
		long userId = authManager.getUserId(authToken);
		HealthRecordDTO hdto = null;
		if (healthRecordId == null) {
			hdto = generateHealthRecordDTO(healthRecordDate, userId, forUserId);
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
		long userId = authManager.getUserId(authToken);
		PrescriptionFileDTO pf = prescriptionFileDAO.findOne(prescriptionFileId);
		validateHealthRecordAccess(pf.getUserprescription().getHealthrecord(), userId);
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
		long userId = authManager.getUserId(authToken);
		DiagnosisReportFileDTO drf = diagnosisReportFileDAO.findOne(diagnosisReportFileId);
		validateHealthRecordAccess(drf.getUserdiagnosisreport().getHealthrecord(), userId);
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
			d.setName(udrdto.getDiagnosisName());
			
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

	@Override
	@Transactional
	public AddShareHealthRecordResponse addShareHealthRecord(String authToken, AddShareHealthRecord request)
			throws Exception {
		long userId = authManager.getUserId(authToken);
		
		//health records will be shared by individuals to the doctor.
		long sharedBy = userId;
		long sharedTo = Long.parseLong(request.getSharedToUserId());
		
		SharedUserRecordDTO surdto = sharedUserRecordDAO.getSharedUserRecord(sharedBy, sharedTo);
		if (surdto == null) {
			surdto = new SharedUserRecordDTO();
			surdto.setSharedBy(userDAO.findOne(sharedBy));
			surdto.setSharedTo(userDAO.findOne(sharedTo));
			surdto = sharedUserRecordDAO.save(surdto);
		}
		
		for (String str: request.getHealthRecordId()) {
			List<SharedHealthRecordDTO> shrdtos = sharedHealthRecordDAO.listSharedHealthRecordByHealthRecordId(Long.parseLong(str));
			SharedHealthRecordDTO shrdto;
			boolean alreadyShared = false;
			for (SharedHealthRecordDTO s: shrdtos) {
				if (s.getShareduserrecord().getSharedTo().getId() == sharedTo) {
					alreadyShared = true;
					break;
				}
			}
			if (alreadyShared) continue;
			
			shrdto = new SharedHealthRecordDTO();
			
			HealthRecordDTO hrdto = healthRecordDAO.findOne(Long.parseLong(str));
			if (hrdto.getUser().getId() != sharedBy) {
				throw new HandledException("NOT_SAME_USER", "Logged in user is not the one whose report is being shared.");
			}
			shrdto.setHealthrecord(hrdto);
			shrdto.setShareduserrecord(surdto);
			sharedHealthRecordDAO.save(shrdto);
		}
		
		AddShareHealthRecordResponse response = new AddShareHealthRecordResponse();
		response.setSharedRecordId(Long.toString(surdto.getId()));
		return ServiceUtils.setResponse(response, true, "Add shared record");
	}

	@Override
	@Transactional
	public GetShareHealthRecordResponse getShareHealthRecord(String authToken, long shareHealthRecordId)
			throws Exception {
		long userId = authManager.getUserId(authToken);
		
		SharedUserRecordDTO dto = sharedUserRecordDAO.findOne(shareHealthRecordId);
		if (dto.getSharedBy().getId() != userId && dto.getSharedTo().getId() != userId) {
			throw new HandledException("INVALID_USER_ACCESS", "This user does not have access to this record");
		}
		GetShareHealthRecordResponse response = new GetShareHealthRecordResponse();
		response.setShareRecordAttrs(populateSharedRecord(dto));
		return ServiceUtils.setResponse(response, true, "Get shared record");
	}
	
	private ShareRecordAttrs populateSharedRecord(SharedUserRecordDTO dto) throws Exception {
		ShareRecordAttrs shr = new ShareRecordAttrs();
		
		UserDTO sharedByDTO = dto.getSharedBy();
		FileStoreDTO profilePicFileStoreDTO = sharedByDTO.getProfileImageFileStore();
		String profileImage = fileStoreManager.retrieveFile(profilePicFileStoreDTO).asCharSource(Charsets.UTF_8).read();
		
		shr.setSharedByUserId(Long.toString(sharedByDTO.getId()));
		shr.setSharedByFirstName(sharedByDTO.getFirstName());
		shr.setSharedByLastName(sharedByDTO.getLastName());
		shr.setSharedByGender(sharedByDTO.getGender());
		shr.setSharedByProfilePic(profileImage);
		
		UserDTO sharedToDTO = dto.getSharedTo();
		profilePicFileStoreDTO = sharedToDTO.getProfileImageFileStore();
		profileImage = fileStoreManager.retrieveFile(profilePicFileStoreDTO).asCharSource(Charsets.UTF_8).read();
		
		shr.setSharedToUserId(Long.toString(sharedToDTO.getId()));
		shr.setSharedToFirstName(sharedToDTO.getFirstName());
		shr.setSharedToLastName(sharedToDTO.getLastName());
		shr.setSharedToGender(sharedToDTO.getGender());
		shr.setSharedToProfilePic(profileImage);
		
		List<SharedHealthRecordDTO> shrdtos = dto.getSharedhealthrecords();
		List<String> hrs = Lists.newArrayList();
		for (SharedHealthRecordDTO shrdto: shrdtos) {
			hrs.add(Long.toString(shrdto.getHealthrecord().getId()));
		}
		
		shr.getHealthRecordId().addAll(hrs);
		return shr;
	}

	@Override
	@Transactional
	public UpdateShareHealthRecordResponse updateShareHealthRecord(String authToken, UpdateShareHealthRecord request)
			throws Exception {
		
		long userId = authManager.getUserId(authToken);
		
		long sharedBy = userId;
		long sharedTo = Long.parseLong(request.getSharedToUserId());
		
		SharedUserRecordDTO surdto = sharedUserRecordDAO.findOne(Long.parseLong(request.getSharedRecordId()));
		if (surdto.getSharedBy().getId() != sharedBy) {
			throw new HandledException("SHARED_BY_CANNOT_BE_CHANGED", "Shared by user cannot be changed.");
		}
		
		surdto = sharedUserRecordDAO.getSharedUserRecord(sharedBy, sharedTo);
		if (surdto == null) {
			surdto = new SharedUserRecordDTO();
			surdto.setSharedBy(userDAO.findOne(sharedBy));
			surdto.setSharedTo(userDAO.findOne(sharedTo));
			surdto = sharedUserRecordDAO.save(surdto);
		}
		
		for (String str: request.getHealthRecordId()) {
			SharedHealthRecordDTO shrdto = new SharedHealthRecordDTO();
			HealthRecordDTO hrdto = healthRecordDAO.findOne(Long.parseLong(str));
			if (hrdto.getUser().getId() != sharedBy) {
				throw new HandledException("NOT_SAME_USER", "Logged in user is not the one whose report is being shared.");
			}
			shrdto.setHealthrecord(hrdto);
			shrdto.setShareduserrecord(surdto);
			sharedHealthRecordDAO.save(shrdto);
		}
		
		UpdateShareHealthRecordResponse response = new UpdateShareHealthRecordResponse();
		return ServiceUtils.setResponse(response, true, "Update shared record");
	}

	@Override
	@Transactional
	public ListShareHealthRecordResponse listShareHealthRecordByMe(String authToken) throws Exception {
		//This function will be used by individual to see what all records are shared by him and to whom
		long userId = authManager.getUserId(authToken);
		
		List<SharedUserRecordDTO> dtos = sharedUserRecordDAO.listRecordsSharedBy(userId);
		List<ShareRecordAttrs> srs = Lists.newArrayList();
		
		//TODO: Improve the return structure, there should only one shared by in the returning pojo
		if (!dtos.isEmpty()) {
			UserDTO sharedByDTO = dtos.get(0).getSharedBy();
			FileStoreDTO profilePicFileStoreDTO = sharedByDTO.getProfileImageFileStore();
			String profileImage = fileStoreManager.retrieveFile(profilePicFileStoreDTO).asCharSource(Charsets.UTF_8).read();
			
			for (SharedUserRecordDTO dto: dtos) {
				ShareRecordAttrs shr = new ShareRecordAttrs();
				shr.setSharedByFirstName(sharedByDTO.getFirstName());
				shr.setSharedByLastName(sharedByDTO.getLastName());
				shr.setSharedByProfilePic(profileImage);
				shr.setSharedByGender(sharedByDTO.getGender());
				srs.add(populateSharedByMeRecord(dto, shr));
			}
		}		
		
		ListShareHealthRecordResponse response = new ListShareHealthRecordResponse();
		response.getShareRecordAttrs().addAll(srs);
		return ServiceUtils.setResponse(response, true, "List record shared by me");
	}
	
	private ShareRecordAttrs populateSharedByMeRecord(SharedUserRecordDTO dto, ShareRecordAttrs shr) throws Exception {
		UserDTO sharedToDTO = dto.getSharedTo();
		FileStoreDTO profilePicFileStoreDTO = sharedToDTO.getProfileImageFileStore();
		String profileImage = fileStoreManager.retrieveFile(profilePicFileStoreDTO).asCharSource(Charsets.UTF_8).read();
		
		shr.setSharedToUserId(Long.toString(sharedToDTO.getId()));
		shr.setSharedToFirstName(sharedToDTO.getFirstName());
		shr.setSharedToLastName(sharedToDTO.getLastName());
		shr.setSharedToGender(sharedToDTO.getGender());
		shr.setSharedToProfilePic(profileImage);
		
		List<SharedHealthRecordDTO> shrdtos = dto.getSharedhealthrecords();
		List<String> hrs = Lists.newArrayList();
		for (SharedHealthRecordDTO shrdto: shrdtos) {
			hrs.add(Long.toString(shrdto.getHealthrecord().getId()));
		}
		
		shr.getHealthRecordId().addAll(hrs);
		return shr;
	}

	@Override
	@Transactional
	public ListShareHealthRecordResponse listShareHealthRecordToMe(String authToken) throws Exception {
		//This function will be used by doctor to see what all records are shared with him and by whom
		long userId = authManager.getUserId(authToken);
		
		List<SharedUserRecordDTO> dtos = sharedUserRecordDAO.listRecordsSharedTo(userId);
		List<ShareRecordAttrs> srs = Lists.newArrayList();

		//TODO: Improve the return structure, there should only one shared to in the returning pojo
		if (!dtos.isEmpty()) {
			UserDTO sharedToDTO = dtos.get(0).getSharedTo();
			FileStoreDTO profilePicFileStoreDTO = sharedToDTO.getProfileImageFileStore();
			String profileImage = fileStoreManager.retrieveFile(profilePicFileStoreDTO).asCharSource(Charsets.UTF_8).read();
			
			for (SharedUserRecordDTO dto: dtos) {
				ShareRecordAttrs shr = new ShareRecordAttrs();
				shr.setSharedToFirstName(sharedToDTO.getFirstName());
				shr.setSharedToLastName(sharedToDTO.getLastName());
				shr.setSharedToGender(sharedToDTO.getGender());
				shr.setSharedToProfilePic(profileImage);
				srs.add(populateSharedToMeRecord(dto, shr));
			}
		}
		
		ListShareHealthRecordResponse response = new ListShareHealthRecordResponse();
		response.getShareRecordAttrs().addAll(srs);
		return ServiceUtils.setResponse(response, true, "List record shared to me");
	}
	
	private ShareRecordAttrs populateSharedToMeRecord(SharedUserRecordDTO dto, ShareRecordAttrs shr) throws Exception {
		UserDTO sharedByDTO = dto.getSharedBy();
		FileStoreDTO profilePicFileStoreDTO = sharedByDTO.getProfileImageFileStore();
		String profileImage = fileStoreManager.retrieveFile(profilePicFileStoreDTO).asCharSource(Charsets.UTF_8).read();
		
		shr.setSharedByUserId(Long.toString(sharedByDTO.getId()));
		shr.setSharedByFirstName(sharedByDTO.getFirstName());
		shr.setSharedByLastName(sharedByDTO.getLastName());
		shr.setSharedByGender(sharedByDTO.getGender());
		shr.setSharedByProfilePic(profileImage);
		
		List<SharedHealthRecordDTO> shrdtos = dto.getSharedhealthrecords();
		List<String> hrs = Lists.newArrayList();
		for (SharedHealthRecordDTO shrdto: shrdtos) {
			hrs.add(Long.toString(shrdto.getHealthrecord().getId()));
		}
		
		shr.getHealthRecordId().addAll(hrs);
		return shr;
	}
	
	@Override
	@Transactional(rollbackOn=Exception.class)
	public GetPrescriptionNameValueResponse addPrescription(String authToken, AddPrescriptionNameValue request)
			throws Exception {
		
		long userId = authManager.getUserId(authToken);
		HealthRecordDTO hdto = null;
		
		if(request.getPrescriptionNameValue()!= null){
			if (request.getHealthRecordId() == null) {
				//TODO Wahid: This can be a new prescription as well, so need to handle that
				hdto = generateHealthRecordDTO(healthRecordDate, userId, forUserId);
			} else {
				hdto = getHealthRecordDTO(Long.parseLong(request.getHealthRecordId()), userId);
			}
			
			PrescriptionNameValue prescriptionNameValue = request.getPrescriptionNameValue();
			UserPrescriptionNameValueDTO prescriptionDTO = new UserPrescriptionNameValueDTO();
			prescriptionDTO.setAdvice(prescriptionNameValue.getAdvice());
			prescriptionDTO.setObservation(prescriptionNameValue.getObservation());
			prescriptionDTO.setPrescriptionName(prescriptionNameValue.getPrescriptionName());
			prescriptionDTO.setHealthrecord(hdto);
			userPrescriptionNameValueDAO.save(prescriptionDTO);

			if (prescriptionNameValue.getPrescriptionRx()!= null && prescriptionNameValue.getPrescriptionRx().size()>0){
				for (PrescriptionRx item: prescriptionNameValue.getPrescriptionRx()) {
					PresciptionRxDTO prxdto = new PresciptionRxDTO();
					prxdto.setMedicineName(item.getMedicineName());
					prxdto.setDosage(item.getDosage());
					prxdto.setDuration(item.getDuration());
					prxdto.setQuantity(item.getQuantity());
					prxdto.setStrength(item.getStrength());
					prxdto.setUserprescriptionnamevalue(prescriptionDTO);
					prescriptionRxDAO.save(prxdto);
				}
			}
			
			healthRecordDAO.save(hdto);

		}
		GetPrescriptionNameValueResponse response = new GetPrescriptionNameValueResponse();
		//TODO: Wahid, we are not putting anything back in the response, is that what we need?
		return ServiceUtils.setResponse(response, true, "Add Prescription name value record");
	}
	
	private List<PrescriptionNameValue> populatePrescriptionNameValue(List<UserPrescriptionNameValueDTO> updtos) {
		List<PrescriptionNameValue> precriptions = Lists.newArrayList();
		if (updtos != null) {
			for(UserPrescriptionNameValueDTO updto: updtos) {
				PrescriptionNameValue prescription = new PrescriptionNameValue();
				prescription.setId(String.valueOf(updto.getId()));
				prescription.setAdvice(updto.getAdvice());
				prescription.setObservation(updto.getObservation());
				prescription.setPrescriptionName(updto.getPrescriptionName());
		
				List<PresciptionRxDTO> prdtos = updto.getPresciptionrxs();
				List<PrescriptionRx> prxs = Lists.newArrayList();
				for (PresciptionRxDTO prdto: prdtos) {
					PrescriptionRx prx = new PrescriptionRx();
					prx.setId(String.valueOf(prdto.getId()));
					prx.setMedicineName(prdto.getMedicineName());
					prx.setDosage(prdto.getDosage());
					prx.setDuration(prdto.getDuration());
					prx.setQuantity(prdto.getQuantity());
					prx.setStrength(prdto.getStrength());
					prxs.add(prx);
				}
				prescription.getPrescriptionRx().addAll(prxs);
				precriptions.add(prescription);
			}
		}
		
		return precriptions;
	}
}
