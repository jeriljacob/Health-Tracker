package com.psquickit.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the healthrecord database table.
 * 
 */
@Entity
@Table(name="healthrecord")
@NamedQuery(name="HealthRecordDTO.findAll", query="SELECT h FROM HealthRecordDTO h")
public class HealthRecordDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Long createdBy;

	private Timestamp createdOn;

	@Temporal(TemporalType.DATE)
	private Timestamp recordDate;

	private Long updatedBy;

	private Timestamp updatedOn;

	//bi-directional many-to-one association to UserDTO
	@ManyToOne
	@JoinColumn(name="UserId")
	private UserDTO user;

	//bi-directional many-to-one association to SharedHealthRecordDTO
	@OneToMany(mappedBy="healthrecord")
	private List<SharedHealthRecordDTO> sharedhealthrecords;

	//bi-directional many-to-one association to UserDiagnosisReportDTO
	@OneToMany(mappedBy="healthrecord")
	private List<UserDiagnosisReportDTO> userdiagnosisreports;

	//bi-directional many-to-one association to UserPrescriptionDTO
	@OneToMany(mappedBy="healthrecord")
	private List<UserPrescriptionDTO> userprescriptions;

	//bi-directional many-to-one association to UserTestNameValueReportDTO
	@OneToMany(mappedBy="healthrecord")
	private List<UserTestNameValueReportDTO> usertestnamevaluereports;

	public HealthRecordDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public UserDTO getUser() {
		return this.user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<SharedHealthRecordDTO> getSharedhealthrecords() {
		return this.sharedhealthrecords;
	}

	public void setSharedhealthrecords(List<SharedHealthRecordDTO> sharedhealthrecords) {
		this.sharedhealthrecords = sharedhealthrecords;
	}

	public SharedHealthRecordDTO addSharedhealthrecord(SharedHealthRecordDTO sharedhealthrecord) {
		getSharedhealthrecords().add(sharedhealthrecord);
		sharedhealthrecord.setHealthrecord(this);

		return sharedhealthrecord;
	}

	public SharedHealthRecordDTO removeSharedhealthrecord(SharedHealthRecordDTO sharedhealthrecord) {
		getSharedhealthrecords().remove(sharedhealthrecord);
		sharedhealthrecord.setHealthrecord(null);

		return sharedhealthrecord;
	}

	public List<UserDiagnosisReportDTO> getUserdiagnosisreports() {
		return this.userdiagnosisreports;
	}

	public void setUserdiagnosisreports(List<UserDiagnosisReportDTO> userdiagnosisreports) {
		this.userdiagnosisreports = userdiagnosisreports;
	}

	public UserDiagnosisReportDTO addUserdiagnosisreport(UserDiagnosisReportDTO userdiagnosisreport) {
		getUserdiagnosisreports().add(userdiagnosisreport);
		userdiagnosisreport.setHealthrecord(this);

		return userdiagnosisreport;
	}

	public UserDiagnosisReportDTO removeUserdiagnosisreport(UserDiagnosisReportDTO userdiagnosisreport) {
		getUserdiagnosisreports().remove(userdiagnosisreport);
		userdiagnosisreport.setHealthrecord(null);

		return userdiagnosisreport;
	}

	public List<UserPrescriptionDTO> getUserprescriptions() {
		return this.userprescriptions;
	}

	public void setUserprescriptions(List<UserPrescriptionDTO> userprescriptions) {
		this.userprescriptions = userprescriptions;
	}

	public UserPrescriptionDTO addUserprescription(UserPrescriptionDTO userprescription) {
		getUserprescriptions().add(userprescription);
		userprescription.setHealthrecord(this);

		return userprescription;
	}

	public UserPrescriptionDTO removeUserprescription(UserPrescriptionDTO userprescription) {
		getUserprescriptions().remove(userprescription);
		userprescription.setHealthrecord(null);

		return userprescription;
	}

	public List<UserTestNameValueReportDTO> getUsertestnamevaluereports() {
		return this.usertestnamevaluereports;
	}

	public void setUsertestnamevaluereports(List<UserTestNameValueReportDTO> usertestnamevaluereports) {
		this.usertestnamevaluereports = usertestnamevaluereports;
	}

	public UserTestNameValueReportDTO addUsertestnamevaluereport(UserTestNameValueReportDTO usertestnamevaluereport) {
		getUsertestnamevaluereports().add(usertestnamevaluereport);
		usertestnamevaluereport.setHealthrecord(this);

		return usertestnamevaluereport;
	}

	public UserTestNameValueReportDTO removeUsertestnamevaluereport(UserTestNameValueReportDTO usertestnamevaluereport) {
		getUsertestnamevaluereports().remove(usertestnamevaluereport);
		usertestnamevaluereport.setHealthrecord(null);

		return usertestnamevaluereport;
	}

}