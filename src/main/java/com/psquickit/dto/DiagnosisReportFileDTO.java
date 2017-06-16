package com.psquickit.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diagnosisreportfile database table.
 * 
 */
@Entity
@Table(name="diagnosisreportfile")
@NamedQuery(name="DiagnosisReportFileDTO.findAll", query="SELECT d FROM DiagnosisReportFileDTO d")
public class DiagnosisReportFileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	//bi-directional many-to-one association to FileStoreDTO
	@ManyToOne
	@JoinColumn(name="FileStoreId")
	private FileStoreDTO filestore;

	//bi-directional many-to-one association to UserDiagnosisReportDTO
	@ManyToOne
	@JoinColumn(name="UserDiagnosisReportId")
	private UserDiagnosisReportDTO userdiagnosisreport;

	public DiagnosisReportFileDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FileStoreDTO getFilestore() {
		return this.filestore;
	}

	public void setFilestore(FileStoreDTO filestore) {
		this.filestore = filestore;
	}

	public UserDiagnosisReportDTO getUserdiagnosisreport() {
		return this.userdiagnosisreport;
	}

	public void setUserdiagnosisreport(UserDiagnosisReportDTO userdiagnosisreport) {
		this.userdiagnosisreport = userdiagnosisreport;
	}

}