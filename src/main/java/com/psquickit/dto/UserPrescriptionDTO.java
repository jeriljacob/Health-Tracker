package com.psquickit.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the userprescription database table.
 * 
 */
@Entity
@Table(name="userprescription")
@NamedQuery(name="UserPrescriptionDTO.findAll", query="SELECT u FROM UserPrescriptionDTO u")
public class UserPrescriptionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	private Long id;

	//bi-directional many-to-one association to PrescriptionFileDTO
	@OneToMany(mappedBy="userprescription")
	private List<PrescriptionFileDTO> prescriptionfiles;

	//bi-directional many-to-one association to HealthRecordDTO
	@ManyToOne
	@JoinColumn(name="HealthRecordId")
	private HealthRecordDTO healthrecord;

	public UserPrescriptionDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PrescriptionFileDTO> getPrescriptionfiles() {
		return this.prescriptionfiles;
	}

	public void setPrescriptionfiles(List<PrescriptionFileDTO> prescriptionfiles) {
		this.prescriptionfiles = prescriptionfiles;
	}

	public PrescriptionFileDTO addPrescriptionfile(PrescriptionFileDTO prescriptionfile) {
		getPrescriptionfiles().add(prescriptionfile);
		prescriptionfile.setUserprescription(this);

		return prescriptionfile;
	}

	public PrescriptionFileDTO removePrescriptionfile(PrescriptionFileDTO prescriptionfile) {
		getPrescriptionfiles().remove(prescriptionfile);
		prescriptionfile.setUserprescription(null);

		return prescriptionfile;
	}

	public HealthRecordDTO getHealthrecord() {
		return this.healthrecord;
	}

	public void setHealthrecord(HealthRecordDTO healthrecord) {
		this.healthrecord = healthrecord;
	}

}