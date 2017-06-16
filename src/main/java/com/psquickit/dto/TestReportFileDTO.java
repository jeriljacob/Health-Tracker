package com.psquickit.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
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