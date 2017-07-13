package com.psquickit.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the userprescriptionnamevalue database table.
 * 
 */
@Entity
@Table(name="userprescriptionnamevalue")
@NamedQuery(name="UserPrescriptionNameValueDTO.findAll", query="SELECT u FROM UserPrescriptionNameValueDTO u")
public class UserPrescriptionNameValueDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	private Long id;

	private String advice;

	private String observation;

	private String prescriptionName;

	//bi-directional many-to-one association to PresciptionRxDTO
	@OneToMany(mappedBy="userprescriptionnamevalue")
	private List<PresciptionRxDTO> presciptionrxs;

	//bi-directional many-to-one association to UserPrescriptionDTO
	@ManyToOne
	@JoinColumn(name="HealthRecordId")
	private HealthRecordDTO healthrecord;

	public UserPrescriptionNameValueDTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdvice() {
		return this.advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getPrescriptionName() {
		return this.prescriptionName;
	}

	public void setPrescriptionName(String prescriptionName) {
		this.prescriptionName = prescriptionName;
	}

	public List<PresciptionRxDTO> getPresciptionrxs() {
		return this.presciptionrxs;
	}

	public void setPresciptionrxs(List<PresciptionRxDTO> presciptionrxs) {
		this.presciptionrxs = presciptionrxs;
	}

	public PresciptionRxDTO addPresciptionrx(PresciptionRxDTO presciptionrx) {
		getPresciptionrxs().add(presciptionrx);
		presciptionrx.setUserprescriptionnamevalue(this);

		return presciptionrx;
	}

	public PresciptionRxDTO removePresciptionrx(PresciptionRxDTO presciptionrx) {
		getPresciptionrxs().remove(presciptionrx);
		presciptionrx.setUserprescriptionnamevalue(null);

		return presciptionrx;
	}

	public HealthRecordDTO getHealthrecord() {
		return healthrecord;
	}

	public void setHealthrecord(HealthRecordDTO healthrecord) {
		this.healthrecord = healthrecord;
	}
	
	
	
	



}