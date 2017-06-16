package com.psquickit.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the userdiagnosisreport database table.
 * 
 */
@Entity
@Table(name="userdiagnosisreport")
@NamedQuery(name="UserDiagnosisReportDTO.findAll", query="SELECT u FROM UserDiagnosisReportDTO u")
public class UserDiagnosisReportDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String diagnosisName;

	//bi-directional many-to-one association to DiagnosisReportFileDTO
	@OneToMany(mappedBy="userdiagnosisreport")
	private List<DiagnosisReportFileDTO> diagnosisreportfiles;

	//bi-directional many-to-one association to HealthRecordDTO
	@ManyToOne
	@JoinColumn(name="HealthRecordId")
	private HealthRecordDTO healthrecord;

	public UserDiagnosisReportDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDiagnosisName() {
		return this.diagnosisName;
	}

	public void setDiagnosisName(String diagnosisName) {
		this.diagnosisName = diagnosisName;
	}

	public List<DiagnosisReportFileDTO> getDiagnosisreportfiles() {
		return this.diagnosisreportfiles;
	}

	public void setDiagnosisreportfiles(List<DiagnosisReportFileDTO> diagnosisreportfiles) {
		this.diagnosisreportfiles = diagnosisreportfiles;
	}

	public DiagnosisReportFileDTO addDiagnosisreportfile(DiagnosisReportFileDTO diagnosisreportfile) {
		getDiagnosisreportfiles().add(diagnosisreportfile);
		diagnosisreportfile.setUserdiagnosisreport(this);

		return diagnosisreportfile;
	}

	public DiagnosisReportFileDTO removeDiagnosisreportfile(DiagnosisReportFileDTO diagnosisreportfile) {
		getDiagnosisreportfiles().remove(diagnosisreportfile);
		diagnosisreportfile.setUserdiagnosisreport(null);

		return diagnosisreportfile;
	}

	public HealthRecordDTO getHealthrecord() {
		return this.healthrecord;
	}

	public void setHealthrecord(HealthRecordDTO healthrecord) {
		this.healthrecord = healthrecord;
	}

}