package com.pon.wallet.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pon.wallet.constant.PayType;
import com.pon.wallet.dto.TransactionReportDTO;
import com.pon.wallet.entity.TransactionReport;
import com.pon.wallet.entity.Wallet;
import com.pon.wallet.repository.TransactionReportRepository;
import com.pon.wallet.repository.WalletRepository;


@Service
public class TransactionReportService {

	@Autowired
	private TransactionReportRepository transactionReportRepository;
	@Autowired
	private WalletRepository walletRepository;
	
	static ModelMapper modelMapper = new ModelMapper();
	static LocalDateTime today = LocalDateTime.now();
	//static LocalTime nowTime = LocalTime.now();
	
	public List<TransactionReportDTO> findlistBySearch(String status, LocalDate stasrtDate, LocalDate endDate) {
		List<TransactionReportDTO> tran_reportDtos = new ArrayList<TransactionReportDTO>();
		boolean statustoreturn = false;
		for (PayType p : PayType.values()){
			if (p.toString().equalsIgnoreCase(status)) {
				statustoreturn = true;
			}
		}
		
		if (!statustoreturn) {
			List<TransactionReport> listtran = transactionReportRepository
					.findByStartDateandEndDateandNostatus(stasrtDate, endDate);
			if (!listtran.isEmpty() || listtran != null) {
				for (TransactionReport transactionReport : listtran) {
					TransactionReportDTO tranReport = new TransactionReportDTO();
					try {
						tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
						tran_reportDtos.add(tranReport);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} return tran_reportDtos;
		} else {
			List<TransactionReport> listtran = transactionReportRepository.findByStartDateandEndDate(
					stasrtDate, endDate, status);
			if (!listtran.isEmpty() || listtran != null) {
				for (TransactionReport transactionReport : listtran) {
					TransactionReportDTO tranReport = new TransactionReportDTO();
					try {
						tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
						tran_reportDtos.add(tranReport);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			
			}
		}return tran_reportDtos;
	}
	public List<TransactionReportDTO> findlistBySearchwithuser(String username, String status, LocalDate stasrtDate,
			LocalDate endDate) {
		Wallet wallet = walletRepository.findByPayer(username);
		List<TransactionReportDTO> tran_reportDtos = new ArrayList<TransactionReportDTO>();
		boolean statustoreturn = false;
		for (PayType p : PayType.values()){
			if (p.toString().equalsIgnoreCase(status)) {
				statustoreturn = true;
			}
		}
		
		if (!statustoreturn) {
			List<TransactionReport> listtran = transactionReportRepository
					.findByUserwitSearchAll(wallet, stasrtDate, endDate);
			if (!listtran.isEmpty() || listtran != null) {
				for (TransactionReport transactionReport : listtran) {
					TransactionReportDTO tranReport = new TransactionReportDTO();
					try {
						tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
						tran_reportDtos.add(tranReport);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} return tran_reportDtos;
		} else {
			List<TransactionReport> listtran = transactionReportRepository.findByUserwithSearchAndStatus(wallet, stasrtDate, endDate, status);
					System.out.println(listtran.size());
			if (!listtran.isEmpty() || listtran != null) {
				for (TransactionReport transactionReport : listtran) {
					TransactionReportDTO tranReport = new TransactionReportDTO();
					try {
						tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
						tran_reportDtos.add(tranReport);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			
			}
		}return tran_reportDtos;
	}
	public List<TransactionReportDTO> getAllTransaction() {
		List<TransactionReportDTO> tranReportDtos = new ArrayList<TransactionReportDTO>();
		for (TransactionReport transactionReport : transactionReportRepository.findAll()) {
			TransactionReportDTO tranReport = new TransactionReportDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
				tranReportDtos.add(tranReport);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return tranReportDtos;
	}
	public List<TransactionReportDTO> findAllWithStatus(TransactionReport transReport) {
		List<TransactionReportDTO> tranReportDtos = new ArrayList<TransactionReportDTO>();
		if(transReport.getStatus()!=null||!transReport.getStatus().isEmpty()){
		for (TransactionReport transactionReport : transactionReportRepository.findAllwithStatus(transReport.getStatus())) {
			TransactionReportDTO tranReport = new TransactionReportDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
				tranReportDtos.add(tranReport);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}}
		return tranReportDtos;
	}
	public List<TransactionReportDTO> gettranToday() {
		List<TransactionReportDTO> tran_reportDtos = new ArrayList<TransactionReportDTO>();
		for (TransactionReport transactionReport : transactionReportRepository.findByCreateDate()) {
			TransactionReportDTO tranReport = new TransactionReportDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
				tran_reportDtos.add(tranReport);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return tran_reportDtos;
	}
	public List<TransactionReportDTO> gettranByUser(String username) {
		Wallet wallet=walletRepository.findByPayer(username);
		System.out.println(wallet.getPayer());
		List<TransactionReportDTO> tranReportDtos = new ArrayList<TransactionReportDTO>();
	
		for (TransactionReport transactionReport : transactionReportRepository.findByWallet(wallet)) {
			TransactionReportDTO tranReport = new TransactionReportDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
				tranReportDtos.add(tranReport);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return tranReportDtos;
	}
	public List<TransactionReportDTO> getByUserwithStatus(TransactionReport transReport) {
		List<TransactionReportDTO> tranReportDtos = new ArrayList<TransactionReportDTO>();
		Wallet wallet =walletRepository.findByPayer(transReport.getPayer());
		System.out.println();
		for (TransactionReport transactionReport : transactionReportRepository.findByUserwithStatus(wallet,transReport.getStatus())) {
			TransactionReportDTO tranReport = new TransactionReportDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
				tranReportDtos.add(tranReport);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return tranReportDtos;
	}
	public List<TransactionReportDTO> getByUserToday(TransactionReport transReport) {
		Wallet wallet =walletRepository.findByPayer(transReport.getPayer());
		List<TransactionReportDTO> tran_reportDtos = new ArrayList<TransactionReportDTO>();
		for (TransactionReport transactionReport : transactionReportRepository.findByUserwithStatusAndDatetoDay(wallet)) {
			TransactionReportDTO tranReport = new TransactionReportDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
				tran_reportDtos.add(tranReport);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return tran_reportDtos;
	}
//	public String delete(String username) {
//		transactionReportRepository.deleteTransByUsername(username);
//		return "Success";
//	}

}
