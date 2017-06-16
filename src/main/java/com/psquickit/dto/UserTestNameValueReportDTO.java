package com.psquickit.dto;

import java.io.Serializable;
import java.util.Date;
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
 * The persistent class for the usertestnamevaluereport database table.
 * 
 */
@Entity
@Table(name="usertestnamevaluereport")
@NamedQuery(name="UserTestNameValueReportDTO.findAll", query="SELECT u FROM UserTestNameValueReportDTO u")
public class UserTestNameValueReportDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	private String testName;

	private String testValue;

	private String testValuesRange;

	private String unit;

	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	//bi-directional many-to-one association to SubTestNameValueDTO
	@OneToMany(mappedBy="usertestnamevaluereport")
	private List<SubTestNameValueDTO> subtestnamevalues;

	//bi-directional many-to-one association to TestReportFileDTO
	@OneToMany(mappedBy="usertestnamevaluereport")
	private List<TestReportFileDTO> testreportfiles;

	//bi-directional many-to-one association to HealthRecordDTO
	@ManyToOne
	@JoinColumn(name="HealthRecordId")
	private HealthRecordDTO healthrecord;

	public UserTestNameValueReportDTO() {
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

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestValue() {
		return this.testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	public String getTestValuesRange() {
		return this.testValuesRange;
	}

	public void setTestValuesRange(String testValuesRange) {
		this.testValuesRange = testValuesRange;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<SubTestNameValueDTO> getSubtestnamevalues() {
		return this.subtestnamevalues;
	}

	public void setSubtestnamevalues(List<SubTestNameValueDTO> subtestnamevalues) {
		this.subtestnamevalues = subtestnamevalues;
	}

	public SubTestNameValueDTO addSubtestnamevalue(SubTestNameValueDTO subtestnamevalue) {
		getSubtestnamevalues().add(subtestnamevalue);
		subtestnamevalue.setUsertestnamevaluereport(this);

		return subtestnamevalue;
	}

	public SubTestNameValueDTO removeSubtestnamevalue(SubTestNameValueDTO subtestnamevalue) {
		getSubtestnamevalues().remove(subtestnamevalue);
		subtestnamevalue.setUsertestnamevaluereport(null);

		return subtestnamevalue;
	}

	public List<TestReportFileDTO> getTestreportfiles() {
		return this.testreportfiles;
	}

	public void setTestreportfiles(List<TestReportFileDTO> testreportfiles) {
		this.testreportfiles = testreportfiles;
	}

	public TestReportFileDTO addTestreportfile(TestReportFileDTO testreportfile) {
		getTestreportfiles().add(testreportfile);
		testreportfile.setUsertestnamevaluereport(this);

		return testreportfile;
	}

	public TestReportFileDTO removeTestreportfile(TestReportFileDTO testreportfile) {
		getTestreportfiles().remove(testreportfile);
		testreportfile.setUsertestnamevaluereport(null);

		return testreportfile;
	}

	public HealthRecordDTO getHealthrecord() {
		return this.healthrecord;
	}

	public void setHealthrecord(HealthRecordDTO healthrecord) {
		this.healthrecord = healthrecord;
	}

}