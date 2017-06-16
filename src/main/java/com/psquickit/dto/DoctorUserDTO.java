package com.psquickit.dto;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the doctoruser database table.
 * 
 */
@Entity
@Table(name="doctoruser")
@NamedQuery(name="DoctorUserDTO.findAll", query="SELECT d FROM DoctorUserDTO d")
public class DoctorUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	private Long id;

	private String clinicAlternateContactNumber;

	private String clinicContactNumber;

	private Long createdBy;
	
	private Timestamp createdOn;

	private String EConsultant;

	private String inPersonConsultant;

	private Long updatedBy;
	
	private Timestamp updatedOn;

	//bi-directional many-to-one association to DoctorClinicAddressDTO
	@OneToMany(mappedBy="doctoruser")
	private List<DoctorClinicAddressDTO> doctorclinicaddresses;

	//bi-directional many-to-one association to DoctorDegreeDTO
	@OneToMany(mappedBy="doctoruser")
	private List<DoctorDegreeDTO> doctordegrees;

	//bi-directional many-to-one association to DoctorMciDTO
	@OneToMany(mappedBy="doctoruser")
	private List<DoctorMciDTO> doctormcis;

	//bi-directional many-to-one association to DoctorSpecializationDTO
	@OneToMany(mappedBy="doctoruser")
	private List<DoctorSpecializationDTO> doctorspecializations;

	//bi-directional many-to-one association to AddressDTO
	@ManyToOne
	@JoinColumn(name="PracticeAreaId")
	private AddressDTO address;

	//bi-directional many-to-one association to UserDTO
	@ManyToOne
	@JoinColumn(name="UserId")
	private UserDTO user;

	public DoctorUserDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClinicAlternateContactNumber() {
		return this.clinicAlternateContactNumber;
	}

	public void setClinicAlternateContactNumber(String clinicAlternateContactNumber) {
		this.clinicAlternateContactNumber = clinicAlternateContactNumber;
	}

	public String getClinicContactNumber() {
		return this.clinicContactNumber;
	}

	public void setClinicContactNumber(String clinicContactNumber) {
		this.clinicContactNumber = clinicContactNumber;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getEConsultant() {
		return this.EConsultant;
	}

	public void setEConsultant(String EConsultant) {
		this.EConsultant = EConsultant;
	}

	public String getInPersonConsultant() {
		return this.inPersonConsultant;
	}

	public void setInPersonConsultant(String inPersonConsultant) {
		this.inPersonConsultant = inPersonConsultant;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<DoctorClinicAddressDTO> getDoctorclinicaddresses() {
		return this.doctorclinicaddresses;
	}

	public void setDoctorclinicaddresses(List<DoctorClinicAddressDTO> doctorclinicaddresses) {
		this.doctorclinicaddresses = doctorclinicaddresses;
	}

	public DoctorClinicAddressDTO addDoctorclinicaddress(DoctorClinicAddressDTO doctorclinicaddress) {
		getDoctorclinicaddresses().add(doctorclinicaddress);
		doctorclinicaddress.setDoctoruser(this);

		return doctorclinicaddress;
	}

	public DoctorClinicAddressDTO removeDoctorclinicaddress(DoctorClinicAddressDTO doctorclinicaddress) {
		getDoctorclinicaddresses().remove(doctorclinicaddress);
		doctorclinicaddress.setDoctoruser(null);

		return doctorclinicaddress;
	}

	public List<DoctorDegreeDTO> getDoctordegrees() {
		return this.doctordegrees;
	}

	public void setDoctordegrees(List<DoctorDegreeDTO> doctordegrees) {
		this.doctordegrees = doctordegrees;
	}

	public DoctorDegreeDTO addDoctordegree(DoctorDegreeDTO doctordegree) {
		getDoctordegrees().add(doctordegree);
		doctordegree.setDoctoruser(this);

		return doctordegree;
	}

	public DoctorDegreeDTO removeDoctordegree(DoctorDegreeDTO doctordegree) {
		getDoctordegrees().remove(doctordegree);
		doctordegree.setDoctoruser(null);

		return doctordegree;
	}

	public List<DoctorMciDTO> getDoctormcis() {
		return this.doctormcis;
	}

	public void setDoctormcis(List<DoctorMciDTO> doctormcis) {
		this.doctormcis = doctormcis;
	}

	public DoctorMciDTO addDoctormci(DoctorMciDTO doctormci) {
		getDoctormcis().add(doctormci);
		doctormci.setDoctoruser(this);

		return doctormci;
	}

	public DoctorMciDTO removeDoctormci(DoctorMciDTO doctormci) {
		getDoctormcis().remove(doctormci);
		doctormci.setDoctoruser(null);

		return doctormci;
	}

	public List<DoctorSpecializationDTO> getDoctorspecializations() {
		return this.doctorspecializations;
	}

	public void setDoctorspecializations(List<DoctorSpecializationDTO> doctorspecializations) {
		this.doctorspecializations = doctorspecializations;
	}

	public DoctorSpecializationDTO addDoctorspecialization(DoctorSpecializationDTO doctorspecialization) {
		getDoctorspecializations().add(doctorspecialization);
		doctorspecialization.setDoctoruser(this);

		return doctorspecialization;
	}

	public DoctorSpecializationDTO removeDoctorspecialization(DoctorSpecializationDTO doctorspecialization) {
		getDoctorspecializations().remove(doctorspecialization);
		doctorspecialization.setDoctoruser(null);

		return doctorspecialization;
	}

	public AddressDTO getAddress() {
		return this.address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public UserDTO getUser() {
		return this.user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}