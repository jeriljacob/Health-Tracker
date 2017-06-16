package com.psquickit.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the degreemaster database table.
 * 
 */
@Entity
@Table(name="degreemaster")
@NamedQuery(name="DegreeMasterDTO.findAll", query="SELECT d FROM DegreeMasterDTO d")
public class DegreeMasterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String degreeName;

	private byte isActive;

	//bi-directional many-to-one association to DoctorDegreeDTO
	@OneToMany(mappedBy="degreemaster")
	private List<DoctorDegreeDTO> doctordegrees;

	public DegreeMasterDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDegreeName() {
		return this.degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public List<DoctorDegreeDTO> getDoctordegrees() {
		return this.doctordegrees;
	}

	public void setDoctordegrees(List<DoctorDegreeDTO> doctordegrees) {
		this.doctordegrees = doctordegrees;
	}

	public DoctorDegreeDTO addDoctordegree(DoctorDegreeDTO doctordegree) {
		getDoctordegrees().add(doctordegree);
		doctordegree.setDegreemaster(this);

		return doctordegree;
	}

	public DoctorDegreeDTO removeDoctordegree(DoctorDegreeDTO doctordegree) {
		getDoctordegrees().remove(doctordegree);
		doctordegree.setDegreemaster(null);

		return doctordegree;
	}

}