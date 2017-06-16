package com.psquickit.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="UserDTO.findAll", query="SELECT u FROM UserDTO u")
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String aadhaarNumber;

	private String alternateContactNumber;

	private String contactNumber;

	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	private String email;

	private String firstName;

	private String lastName;

	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	private String userType;

	//bi-directional many-to-one association to DoctorUserDTO
	@OneToMany(mappedBy="user")
	private List<DoctorUserDTO> doctorusers;

	//bi-directional many-to-one association to HealthRecordDTO
	@OneToMany(mappedBy="user")
	private List<HealthRecordDTO> healthrecords;

	//bi-directional many-to-one association to SharedUserRecordDTO
	@OneToMany(mappedBy="user1")
	private List<SharedUserRecordDTO> shareduserrecords1;

	//bi-directional many-to-one association to SharedUserRecordDTO
	@OneToMany(mappedBy="user2")
	private List<SharedUserRecordDTO> shareduserrecords2;

	//bi-directional many-to-one association to AddressDTO
	@ManyToOne
	@JoinColumn(name="AlternateAddressId")
	private AddressDTO address1;

	//bi-directional many-to-one association to AddressDTO
	@ManyToOne
	@JoinColumn(name="PermanentAddressId")
	private AddressDTO address2;

	//bi-directional many-to-one association to FileStoreDTO
	@ManyToOne
	@JoinColumn(name="ProfileImageFileStoreId")
	private FileStoreDTO profileImageFileStore;

	public UserDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAadhaarNumber() {
		return this.aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getAlternateContactNumber() {
		return this.alternateContactNumber;
	}

	public void setAlternateContactNumber(String alternateContactNumber) {
		this.alternateContactNumber = alternateContactNumber;
	}

	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<DoctorUserDTO> getDoctorusers() {
		return this.doctorusers;
	}

	public void setDoctorusers(List<DoctorUserDTO> doctorusers) {
		this.doctorusers = doctorusers;
	}

	public DoctorUserDTO addDoctoruser(DoctorUserDTO doctoruser) {
		getDoctorusers().add(doctoruser);
		doctoruser.setUser(this);

		return doctoruser;
	}

	public DoctorUserDTO removeDoctoruser(DoctorUserDTO doctoruser) {
		getDoctorusers().remove(doctoruser);
		doctoruser.setUser(null);

		return doctoruser;
	}

	public List<HealthRecordDTO> getHealthrecords() {
		return this.healthrecords;
	}

	public void setHealthrecords(List<HealthRecordDTO> healthrecords) {
		this.healthrecords = healthrecords;
	}

	public HealthRecordDTO addHealthrecord(HealthRecordDTO healthrecord) {
		getHealthrecords().add(healthrecord);
		healthrecord.setUser(this);

		return healthrecord;
	}

	public HealthRecordDTO removeHealthrecord(HealthRecordDTO healthrecord) {
		getHealthrecords().remove(healthrecord);
		healthrecord.setUser(null);

		return healthrecord;
	}

	public List<SharedUserRecordDTO> getShareduserrecords1() {
		return this.shareduserrecords1;
	}

	public void setShareduserrecords1(List<SharedUserRecordDTO> shareduserrecords1) {
		this.shareduserrecords1 = shareduserrecords1;
	}

	public SharedUserRecordDTO addShareduserrecords1(SharedUserRecordDTO shareduserrecords1) {
		getShareduserrecords1().add(shareduserrecords1);
		shareduserrecords1.setUser1(this);

		return shareduserrecords1;
	}

	public SharedUserRecordDTO removeShareduserrecords1(SharedUserRecordDTO shareduserrecords1) {
		getShareduserrecords1().remove(shareduserrecords1);
		shareduserrecords1.setUser1(null);

		return shareduserrecords1;
	}

	public List<SharedUserRecordDTO> getShareduserrecords2() {
		return this.shareduserrecords2;
	}

	public void setShareduserrecords2(List<SharedUserRecordDTO> shareduserrecords2) {
		this.shareduserrecords2 = shareduserrecords2;
	}

	public SharedUserRecordDTO addShareduserrecords2(SharedUserRecordDTO shareduserrecords2) {
		getShareduserrecords2().add(shareduserrecords2);
		shareduserrecords2.setUser2(this);

		return shareduserrecords2;
	}

	public SharedUserRecordDTO removeShareduserrecords2(SharedUserRecordDTO shareduserrecords2) {
		getShareduserrecords2().remove(shareduserrecords2);
		shareduserrecords2.setUser2(null);

		return shareduserrecords2;
	}

	public AddressDTO getAddress1() {
		return this.address1;
	}

	public void setAddress1(AddressDTO address1) {
		this.address1 = address1;
	}

	public AddressDTO getAddress2() {
		return this.address2;
	}

	public void setAddress2(AddressDTO address2) {
		this.address2 = address2;
	}

	public FileStoreDTO getProfileImageFileStore() {
		return this.profileImageFileStore;
	}

	public void setProfileImageFileStore(FileStoreDTO profileImageFileStore) {
		this.profileImageFileStore = profileImageFileStore;
	}

}