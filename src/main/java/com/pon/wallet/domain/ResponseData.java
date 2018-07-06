package com.pon.wallet.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
	private boolean status;
	private T data;
	private String messsage;
}
