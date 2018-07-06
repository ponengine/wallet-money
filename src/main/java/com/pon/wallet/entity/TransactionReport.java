package com.pon.wallet.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private int  moneyWallet;
	private String status;
	private String usernameBuyer;
	private String usernameSeller;
	@Transient
	private LocalDate stasrtDate;
	@Transient
	private LocalDate endDate;

	public TransactionReport() {
	}



	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;
	
}
