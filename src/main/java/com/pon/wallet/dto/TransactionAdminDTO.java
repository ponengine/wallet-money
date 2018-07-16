package com.pon.wallet.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TransactionAdminDTO {
	private Long id;
	private LocalDate createDate;
	private LocalTime createTime;
	private double  moneyWallet;
	private String status;
	private String usernameBuyer;
	private String usernameSeller;
	private String note;
	private String referenceId;
	private String referencetran;
	private String editBy;
	private LocalDate editDate;
	private LocalTime editTime;
}
