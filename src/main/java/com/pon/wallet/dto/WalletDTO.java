package com.pon.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletDTO {
	private Long id;
	private int walletMoney;
	private String usernameBuyer;
	private String usernameSeller;
}
