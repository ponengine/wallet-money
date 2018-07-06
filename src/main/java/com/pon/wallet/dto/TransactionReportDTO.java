package com.pon.wallet.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import com.pon.wallet.constant.PayType;
@Getter
@Setter
public class TransactionReportDTO {

	private Long id;
	private String createDate;
	private int  moneyWallet;
	private String status;
	private String usernameBuyer;
	private String usernameSeller;
}
