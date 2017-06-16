package com.psquickit.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the testreportfile database table.
 * 
 */
@Entity
@Table(name="testreportfile")
@NamedQuery(name="TestReportFileDTO.findAll", query="SELECT t FROM TestReportFileDTO t")
public class TestReportFileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	//bi-directional many-to-one association to FileStoreDTO
	@ManyToOne
	@JoinColumn(name="FileStoreId")
	private FileStoreDTO filestore;

	//bi-directional many-to-one association to UserTestNameValueReportDTO
	@ManyToOne
	@JoinColumn(name="UserTestNameValueReportId")
	private UserTestNameValueReportDTO usertestnamevaluereport;

	public TestReportFileDTO() {
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

	public UserTestNameValueReportDTO getUsertestnamevaluereport() {
		return this.usertestnamevaluereport;
	}

	public void setUsertestnamevaluereport(UserTestNameValueReportDTO usertestnamevaluereport) {
		this.usertestnamevaluereport = usertestnamevaluereport;
	}

}