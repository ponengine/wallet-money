package com.pon.wallet.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EditTransaction {
	private Long id;
	private String oldReference;
	private String newReference;
	private LocalDate dateEdit;
	private LocalTime timeEdit;
	
	public EditTransaction() {
		super();
	}
//	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "wallet_id")
//	private EditTransaction editTransaction;
	
	
}
