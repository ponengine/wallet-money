package com.pon.wallet.constant;

public enum Status {
	
	ACTIVE("A"),
	INACTIVE("I"),
	BACKLIST("B");
	
	private String status;

	public String getStatus() {
		return status;
	}

	private Status(String status) {
		this.status = status;
	}
}
