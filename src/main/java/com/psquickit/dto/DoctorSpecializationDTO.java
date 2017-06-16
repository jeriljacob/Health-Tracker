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
 * The persistent class for the doctorspecialization database table.
 * 
 */
@Entity
@Table(name="doctorspecialization")
@NamedQuery(name="DoctorSpecializationDTO.findAll", query="SELECT d FROM DoctorSpecializationDTO d")
public class DoctorSpecializationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	//bi-directional many-to-one association to DoctorUserDTO
	@ManyToOne
	@JoinColumn(name="DoctorUserId")
	private DoctorUserDTO doctoruser;

	//bi-directional many-to-one association to SpecializationMasterDTO
	@ManyToOne
	@JoinColumn(name="SpecializationMasterId")
	private SpecializationMasterDTO specializationmaster;

	public DoctorSpecializationDTO() {
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

	public DoctorUserDTO getDoctoruser() {
		return this.doctoruser;
	}

	public void setDoctoruser(DoctorUserDTO doctoruser) {
		this.doctoruser = doctoruser;
	}

	public SpecializationMasterDTO getSpecializationmaster() {
		return this.specializationmaster;
	}

	public void setSpecializationmaster(SpecializationMasterDTO specializationmaster) {
		this.specializationmaster = specializationmaster;
	}

}