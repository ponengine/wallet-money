package com.pon.wallet.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pon.wallet.domain.BaseRestApi;
import com.pon.wallet.dto.TransactionReportDTO;
import com.pon.wallet.entity.TransactionReport;
import com.pon.wallet.service.TransactionReportService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionReportController {

	@Autowired
	private TransactionReportService transactionReportService;

	//Admin
	@GetMapping("/admin/getall")
	public List<TransactionAdminDTO> getalltransaction() {
		return transactionReportService.getAllTransaction();
	}

//	@PostMapping("/getalltranwithstatus")
//	public List<TransactionReportDTO> getalltranwithstatus(@RequestBody TransactionReport transReport) {
//		return transactionReportService.findAllWithStatus(transReport);
//	}

	@GetMapping("/admin/gettoday")
	public List<TransactionAdminDTO> gettran_today() {
		return transactionReportService.gettranToday();
	}

	@PostMapping("/admin/getbysearch")
	public List<TransactionAdminDTO>getbysearch(@RequestBody TransactionReport searchDate) {
		return  transactionReportService.findlistBySearch(searchDate.getStatus(),searchDate.getStasrtDate(), searchDate.getEndDate());
	}

	@PostMapping("/admin/disableuser")
	public BaseRestApi disableUser(String seaechpayer) {
		return transactionReportService.findByUserToDisable(seaechpayer);
	}
//	@DeleteMapping("/admin/deletebyuser/{usernameBuyer}")
//	public String deleteTransByUsernameBuyer(@PathVariable("usernameBuyer") String username) {
//		return transactionReportService.delete(username);
//	}

	//User
//	@PostMapping("/user/getallbyuser")
//	public List<TransactionReportDTO> getTransaction(@RequestBody TransactionReport transReport) {
//		return transactionReportService.gettranByUser(transReport.getPayer());
//	}
	@GetMapping("/user/getallbyuser/{username}")
	public List<TransactionReportDTO> getTransaction(@PathVariable("username") String username) {
		return transactionReportService.gettranByUser(username);
	}

//	@PostMapping("/getallbyuserwithstatus")
//	public List<TransactionReportDTO> getallbyuserwithstatus(@RequestBody TransactionReport transReport) {
//		return transactionReportService.getByUserwithStatus(transReport);
//	}

	@PostMapping("/user/getusertrantoday")
	public List<TransactionReportDTO> getusertrantoday(@RequestBody TransactionReport transReport) {
		return transactionReportService.getByUserToday(transReport);
	}

	@PostMapping("/user/getbysearchwithuser")
	public List<TransactionReportDTO> getbysearchwithuser(@RequestBody TransactionReport transReport) {
		return transactionReportService.findlistBySearchwithuser(transReport.getPayer(),transReport.getStatus(),transReport.getStasrtDate(),transReport.getEndDate());
	}

}
