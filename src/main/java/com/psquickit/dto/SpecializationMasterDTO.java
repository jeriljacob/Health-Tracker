package com.psquickit.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the specializationmaster database table.
 * 
 */
@Entity
@Table(name="specializationmaster")
@NamedQuery(name="SpecializationMasterDTO.findAll", query="SELECT s FROM SpecializationMasterDTO s")
public class SpecializationMasterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private byte isActive;

	private String specializationName;

	//bi-directional many-to-one association to DoctorSpecializationDTO
	@OneToMany(mappedBy="specializationmaster")
	private List<DoctorSpecializationDTO> doctorspecializations;

	public SpecializationMasterDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public String getSpecializationName() {
		return this.specializationName;
	}

	public void setSpecializationName(String specializationName) {
		this.specializationName = specializationName;
	}

	public List<DoctorSpecializationDTO> getDoctorspecializations() {
		return this.doctorspecializations;
	}

	public void setDoctorspecializations(List<DoctorSpecializationDTO> doctorspecializations) {
		this.doctorspecializations = doctorspecializations;
	}

	public DoctorSpecializationDTO addDoctorspecialization(DoctorSpecializationDTO doctorspecialization) {
		getDoctorspecializations().add(doctorspecialization);
		doctorspecialization.setSpecializationmaster(this);

		return doctorspecialization;
	}

	public DoctorSpecializationDTO removeDoctorspecialization(DoctorSpecializationDTO doctorspecialization) {
		getDoctorspecializations().remove(doctorspecialization);
		doctorspecialization.setSpecializationmaster(null);

		return doctorspecialization;
	}

}