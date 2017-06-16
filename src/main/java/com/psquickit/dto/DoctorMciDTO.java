package com.psquickit.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the doctormci database table.
 * 
 */
@Entity
@Table(name="doctormci")
@NamedQuery(name="DoctorMciDTO.findAll", query="SELECT d FROM DoctorMciDTO d")
public class DoctorMciDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Long createdBy;

	
	private Timestamp createdOn;

	private String registrationNumber;

	private Long updatedBy;

	
	private Timestamp updatedOn;

	//bi-directional many-to-one association to DoctorUserDTO
	@ManyToOne
	@JoinColumn(name="DoctorUserId")
	private DoctorUserDTO doctoruser;

	//bi-directional many-to-one association to MciMasterDTO
	@ManyToOne
	@JoinColumn(name="MciMasterId")
	private MciMasterDTO mcimaster;

	public DoctorMciDTO() {
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

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
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

	public DoctorUserDTO getDoctoruser() {
		return this.doctoruser;
	}

	public void setDoctoruser(DoctorUserDTO doctoruser) {
		this.doctoruser = doctoruser;
	}

	public MciMasterDTO getMcimaster() {
		return this.mcimaster;
	}

	public void setMcimaster(MciMasterDTO mcimaster) {
		this.mcimaster = mcimaster;
	}

}