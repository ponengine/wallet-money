package com.pon.wallet.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pon.wallet.constant.PayType;
import com.pon.wallet.constant.Status;
import com.pon.wallet.domain.BaseResponse;
import com.pon.wallet.domain.BaseRestApi;
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

	public List<TransactionAdminDTO> findlistBySearch(String status, LocalDate stasrtDate, LocalDate endDate) {
		List<TransactionAdminDTO> tran_reportDtos = new ArrayList<TransactionAdminDTO>();
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
					TransactionAdminDTO tranReport = new TransactionAdminDTO();
					try {
						tranReport = modelMapper.map(transactionReport, TransactionAdminDTO.class);
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
					TransactionAdminDTO tranReport = new TransactionAdminDTO();
					try {
						tranReport = modelMapper.map(transactionReport, TransactionAdminDTO.class);
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
	public List<TransactionAdminDTO> getAllTransaction() {
		List<TransactionAdminDTO> tranReportDtos = new ArrayList<TransactionAdminDTO>();
		for (TransactionReport transactionReport : transactionReportRepository.findAll()) {
			TransactionAdminDTO tranReport = new TransactionAdminDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionAdminDTO.class);
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
	public List<TransactionAdminDTO> gettranToday() {
		List<TransactionAdminDTO> tran_reportDtos = new ArrayList<TransactionAdminDTO>();
		for (TransactionReport transactionReport : transactionReportRepository.findByCreateDate()) {
			TransactionAdminDTO tranReport = new TransactionAdminDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionAdminDTO.class);
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
	public BaseRestApi findByUserToDisable(String username) {
		BaseRestApi baseRestApi = new BaseRestApi();
		BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
		Map<String, Object> model = new HashMap<>();

		Wallet wallet = walletRepository.findByPayer(username);
		System.out.println(wallet.getPayer());
		List<TransactionReportDTO> tran_reportDtos = new ArrayList<TransactionReportDTO>();
//		if (payer.getPayer() == null || payer.getPayer().isEmpty()) {
//			baseRestApi.setSuccess(false);
//			return baseRestApi;
//		}
		for (TransactionReport transactionReport : transactionReportRepository.findByPayer(wallet.getPayer())) {
			TransactionReportDTO tranReport = new TransactionReportDTO();
			try {
				tranReport = modelMapper.map(transactionReport, TransactionReportDTO.class);
				tran_reportDtos.add(tranReport);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		model.put("tran", tran_reportDtos);
		wallet.setStatus(Status.INACTIVE.toString());
		baseRestApi.setSuccess(true);
		baseResponse.setData(model);
		baseRestApi.setResponse(baseResponse);
		return baseRestApi;
	}

}
