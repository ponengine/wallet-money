package com.pon.wallet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dealing {
	private int price;
	private String usernameBuyer;
	private String usernameSeller;
	private String note;
	
}
