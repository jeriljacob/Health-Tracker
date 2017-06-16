package com.psquickit.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the prescriptionfile database table.
 * 
 */
@Entity
@Table(name="prescriptionfile")
@NamedQuery(name="PrescriptionFileDTO.findAll", query="SELECT p FROM PrescriptionFileDTO p")
public class PrescriptionFileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	//bi-directional many-to-one association to FileStoreDTO
	@ManyToOne
	@JoinColumn(name="FileStoreId")
	private FileStoreDTO filestore;

	//bi-directional many-to-one association to UserPrescriptionDTO
	@ManyToOne
	@JoinColumn(name="UserPrescriptionId")
	private UserPrescriptionDTO userprescription;

	public PrescriptionFileDTO() {
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

	public UserPrescriptionDTO getUserprescription() {
		return this.userprescription;
	}

	public void setUserprescription(UserPrescriptionDTO userprescription) {
		this.userprescription = userprescription;
	}

}