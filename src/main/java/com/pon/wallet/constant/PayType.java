package com.pon.wallet.constant;

public enum PayType {
	
	ADD("A"),
	SPEND("S"),
	EXCHANGE("E"),
	WITHDRAW("W"),
	REFUND("R");
	
	private String type;

	public String getType() {
		return type;
	}

	private PayType(String type) {
		this.type = type;
	}
	
	
}
