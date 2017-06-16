package com.psquickit.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@Table(name="address")
@NamedQuery(name="AddressDTO.findAll", query="SELECT a FROM AddressDTO a")
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	private Long id;

	private String city;

	private Long createdBy;

	private Timestamp createdOn;

	private String district;

	private String pincode;

	private String state;

	private String street;

	private Long updatedBy;

	private Timestamp updatedOn;

	//bi-directional many-to-one association to DoctorClinicAddressDTO
	@OneToMany(mappedBy="address")
	private List<DoctorClinicAddressDTO> doctorclinicaddresses;

	//bi-directional many-to-one association to DoctorUserDTO
	@OneToMany(mappedBy="address")
	private List<DoctorUserDTO> doctorusers;

	//bi-directional many-to-one association to UserDTO
	@OneToMany(mappedBy="alternateAddress")
	private List<UserDTO> users1;

	//bi-directional many-to-one association to UserDTO
	@OneToMany(mappedBy="permanentAddress")
	private List<UserDTO> users2;

	public AddressDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
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
		doctorclinicaddress.setAddress(this);

		return doctorclinicaddress;
	}

	public DoctorClinicAddressDTO removeDoctorclinicaddress(DoctorClinicAddressDTO doctorclinicaddress) {
		getDoctorclinicaddresses().remove(doctorclinicaddress);
		doctorclinicaddress.setAddress(null);

		return doctorclinicaddress;
	}

	public List<DoctorUserDTO> getDoctorusers() {
		return this.doctorusers;
	}

	public void setDoctorusers(List<DoctorUserDTO> doctorusers) {
		this.doctorusers = doctorusers;
	}

	public DoctorUserDTO addDoctoruser(DoctorUserDTO doctoruser) {
		getDoctorusers().add(doctoruser);
		doctoruser.setAddress(this);

		return doctoruser;
	}

	public DoctorUserDTO removeDoctoruser(DoctorUserDTO doctoruser) {
		getDoctorusers().remove(doctoruser);
		doctoruser.setAddress(null);

		return doctoruser;
	}

	public List<UserDTO> getUsers1() {
		return this.users1;
	}

	public void setUsers1(List<UserDTO> users1) {
		this.users1 = users1;
	}

	public UserDTO addUsers1(UserDTO users1) {
		getUsers1().add(users1);
		users1.setAlternateAddress(this);

		return users1;
	}

	public UserDTO removeUsers1(UserDTO users1) {
		getUsers1().remove(users1);
		users1.setAlternateAddress(null);

		return users1;
	}

	public List<UserDTO> getUsers2() {
		return this.users2;
	}

	public void setUsers2(List<UserDTO> users2) {
		this.users2 = users2;
	}

	public UserDTO addUsers2(UserDTO users2) {
		getUsers2().add(users2);
		users2.setPermanentAddress(this);

		return users2;
	}

	public UserDTO removeUsers2(UserDTO users2) {
		getUsers2().remove(users2);
		users2.setPermanentAddress(null);

		return users2;
	}

}