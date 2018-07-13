package com.pon.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="wallet")
public class Wallet implements Serializable{
	
	
	public Wallet() {

	}
	@Id
	@GeneratedValue
	private Long id;
	private double money;
	private String payer;
	//private String receiver;
	private String status;
	
	@OneToMany(mappedBy = "wallet")
	private List<TransactionReport> transactionReport;
	
}
