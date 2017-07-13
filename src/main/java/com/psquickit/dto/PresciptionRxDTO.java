package com.psquickit.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the presciptionrx database table.
 * 
 */
@Entity
@Table(name="presciptionrx")
@NamedQuery(name="PresciptionRxDTO.findAll", query="SELECT p FROM PresciptionRxDTO p")
public class PresciptionRxDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	private Long id;

	private String dosage;

	private String duration;

	private String medicineName;

	private String quantity;

	private String strength;

	//bi-directional many-to-one association to UserPrescriptionNameValueDTO
	@ManyToOne
	@JoinColumn(name="ParentUserPrescriptonNameValueId")
	private UserPrescriptionNameValueDTO userprescriptionnamevalue;

	public PresciptionRxDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDosage() {
		return this.dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getMedicineName() {
		return this.medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getStrength() {
		return this.strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	/*public UserPrescriptionNameValueDTO getUserprescriptionnamevalue1() {
		return userprescriptionnamevalue;
	}

	public void setUserprescriptionnamevalue1(UserPrescriptionNameValueDTO userprescriptionnamevalue1) {
		this.userprescriptionnamevalue = userprescriptionnamevalue1;
	}*/
	
	

	public UserPrescriptionNameValueDTO getUserprescriptionnamevalue() {
		return this.userprescriptionnamevalue;
	}

	public void setUserprescriptionnamevalue(UserPrescriptionNameValueDTO userprescriptionnamevalue) {
		this.userprescriptionnamevalue = userprescriptionnamevalue;
	}

}