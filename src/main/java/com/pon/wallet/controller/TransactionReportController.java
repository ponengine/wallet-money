package com.pon.wallet.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pon.wallet.dto.TransactionReportDTO;
import com.pon.wallet.entity.TransactionReport;
import com.pon.wallet.service.TransactionReportService;

@RestController
@RequestMapping("/transaction")
public class TransactionReportController {

	@Autowired
	private TransactionReportService transactionReportService;
	
	//Admin
	@GetMapping("/getalltran")
	public List<TransactionReportDTO> getalltransaction() {
		return transactionReportService.getAllTransaction();
	}
	
//	@PostMapping("/getalltranwithstatus")
//	public List<TransactionReportDTO> getalltranwithstatus(@RequestBody TransactionReport transReport) {
//		return transactionReportService.findAllWithStatus(transReport);
//	}

	@GetMapping("/gettrantoday")
	public List<TransactionReportDTO> gettran_today() {
		return transactionReportService.gettranToday();
	}

	@PostMapping("/getbysearch")
	public List<TransactionReportDTO>getbysearch(@RequestBody TransactionReport searchDate) {
		return  transactionReportService.findlistBySearch(searchDate.getStatus(),searchDate.getStasrtDate(), searchDate.getEndDate());
	}
	
	//User
	@PostMapping("/getallbyuser")
	public List<TransactionReportDTO> getTransaction(@RequestBody TransactionReport transReport) {
		return transactionReportService.gettranByUser(transReport.getUsernameBuyer());
	}
	
	@PostMapping("/getallbyuserwithstatus")
	public List<TransactionReportDTO> getallbyuserwithstatus(@RequestBody TransactionReport transReport) {
		return transactionReportService.getByUserwithStatus(transReport);
	}

	@PostMapping("/getusertrantoday")
	public List<TransactionReportDTO> getusertrantoday(@RequestBody TransactionReport transReport) {
		return transactionReportService.getByUserToday(transReport);
	}
	
	@PostMapping("/getbysearchwithuser")
	public List<TransactionReportDTO> getbysearchwithuser(@RequestBody TransactionReport transReport) {
		return transactionReportService.findlistBySearchwithuser(transReport.getUsernameBuyer(),transReport.getStatus(),transReport.getStasrtDate(),transReport.getEndDate());
	}

}
