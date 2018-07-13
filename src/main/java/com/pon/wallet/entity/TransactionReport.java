package com.pon.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="transactionreport")
public class TransactionReport implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name = "tran_id")
	private Long id;
	private LocalDate createDate;
	private LocalTime createTime;
	private double  money;
	private String status;
	private String payer;
	private String receiver;
	private String note;
	private String referenceId;
	private String referencetran;
	private String editBy;
	private LocalDate editDate;
	private LocalTime editTime;
	@Transient
	private LocalDate stasrtDate;
	@Transient
	private LocalDate endDate;

	public TransactionReport() {
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;
	
//	@OneToMany(mappedBy = "wallet")
//	private List<EditTransaction> editTransaction;
	
}
