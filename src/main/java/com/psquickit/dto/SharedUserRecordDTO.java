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
 * The persistent class for the shareduserrecord database table.
 * 
 */
@Entity
@Table(name="shareduserrecord")
@NamedQuery(name="SharedUserRecordDTO.findAll", query="SELECT s FROM SharedUserRecordDTO s")
public class SharedUserRecordDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	private Long id;

	//bi-directional many-to-one association to SharedHealthRecordDTO
	@OneToMany(mappedBy="shareduserrecord")
	private List<SharedHealthRecordDTO> sharedhealthrecords;

	//bi-directional many-to-one association to UserDTO
	@ManyToOne
	@JoinColumn(name="SharedById")
	private UserDTO user1;

	//bi-directional many-to-one association to UserDTO
	@ManyToOne
	@JoinColumn(name="SharedToId")
	private UserDTO user2;

	public SharedUserRecordDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SharedHealthRecordDTO> getSharedhealthrecords() {
		return this.sharedhealthrecords;
	}

	public void setSharedhealthrecords(List<SharedHealthRecordDTO> sharedhealthrecords) {
		this.sharedhealthrecords = sharedhealthrecords;
	}

	public SharedHealthRecordDTO addSharedhealthrecord(SharedHealthRecordDTO sharedhealthrecord) {
		getSharedhealthrecords().add(sharedhealthrecord);
		sharedhealthrecord.setShareduserrecord(this);

		return sharedhealthrecord;
	}

	public SharedHealthRecordDTO removeSharedhealthrecord(SharedHealthRecordDTO sharedhealthrecord) {
		getSharedhealthrecords().remove(sharedhealthrecord);
		sharedhealthrecord.setShareduserrecord(null);

		return sharedhealthrecord;
	}

	public UserDTO getUser1() {
		return this.user1;
	}

	public void setUser1(UserDTO user1) {
		this.user1 = user1;
	}

	public UserDTO getUser2() {
		return this.user2;
	}

	public void setUser2(UserDTO user2) {
		this.user2 = user2;
	}

}