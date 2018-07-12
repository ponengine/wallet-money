package com.pon.wallet.constant;

public enum PayType {
	
	ADD("A"),
	SPEND("S"),
	WITHDRAW("W"),
	RECEIVE("E"),
	CANCEL("C"),
	PAYER("P"),
	REFUND("R");
	
	private String type;

	public String getType() {
		return type;
	}

	private PayType(String type) {
		this.type = type;
	}
	
	
}
