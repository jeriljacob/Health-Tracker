package com.psquickit.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the subtestnamevalue database table.
 * 
 */
@Entity
@Table(name="subtestnamevalue")
@NamedQuery(name="SubTestNameValueDTO.findAll", query="SELECT s FROM SubTestNameValueDTO s")
public class SubTestNameValueDTO implements Serializable {
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

	//bi-directional many-to-one association to UserTestNameValueReportDTO
	@ManyToOne
	@JoinColumn(name="ParentUserTestNameValueReportId")
	private UserTestNameValueReportDTO usertestnamevaluereport;

	public SubTestNameValueDTO() {
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

	public UserTestNameValueReportDTO getUsertestnamevaluereport() {
		return this.usertestnamevaluereport;
	}

	public void setUsertestnamevaluereport(UserTestNameValueReportDTO usertestnamevaluereport) {
		this.usertestnamevaluereport = usertestnamevaluereport;
	}

}