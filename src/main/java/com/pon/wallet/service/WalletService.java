package com.pon.wallet.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pon.wallet.constant.PayType;
import com.pon.wallet.domain.BaseResponse;
import com.pon.wallet.domain.BaseRestApi;
import com.pon.wallet.dto.TransactionReportDTO;
import com.pon.wallet.dto.WalletDTO;
import com.pon.wallet.entity.TransactionReport;
import com.pon.wallet.entity.Wallet;
import com.pon.wallet.repository.TransactionReportRepository;
import com.pon.wallet.repository.WalletRepository;

@Service
public class WalletService {

	
	ModelMapper modelmapper = new ModelMapper();
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private TransactionReportRepository transactionReportRepository;
	@Autowired
	private Environment env;
	public String addWalletService(WalletDTO addwallet) {
		LocalDate today = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(addwallet.getMoney()<=0){
			return "Amount must be greater than 0";
		}
		Wallet wallet = walletRepository.findByPayer(addwallet.getPayer());
		if (wallet == null) {
			return "User not found";
		}
//		String uri = env.getProperty("uri.mainwallet")+"/mainwallet/getmainwallet";	
//		RestTemplate rt = new RestTemplate();
//		double response = rt.getForObject( uri , int.class );
//		if(response<addwallet.getMoney()){
//			return "Not enough money for service.";
//		}
//		response=response-addwallet.getMoney();
//		HttpEntity<String> entity = new HttpEntity<String>(Double.toString(response) ,headers);
//		String uri2 = env.getProperty("uri.mainwallet")+"/mainwallet/getmainwallet";
//		JSONObject obj = new JSONObject();
//		obj.put("money", response);		
//		String status = rt.postForObject(uri2, obj.toString(), String.class);
		wallet.setMoney(wallet.getMoney() + addwallet.getMoney());
		TransactionReport tran_report = new TransactionReport();
		tran_report.setCreateDate(today);
		tran_report.setCreateTime(nowTime);
		tran_report.setMoney(addwallet.getMoney());
		tran_report.setStatus(PayType.ADD.toString());
		tran_report.setPayer(wallet.getPayer());
		tran_report.setWallet(wallet);
		transactionReportRepository.save(tran_report);
		return "Success";
	}
	
	public String withdrawWalletService(WalletDTO withdraw) {
		LocalDate today = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		Wallet buyer = walletRepository.findByPayer(withdraw.getPayer());
		if(withdraw.getMoney()<=0){
			return "Amount must be greater than 0";
		}
		if (buyer == null) {
			return "User not found.";
		}
		if (!(buyer.getMoney() >= withdraw.getMoney()) ) {
			return "Money is not enough.";
		}
		buyer.setMoney(buyer.getMoney() - withdraw.getMoney());
		TransactionReport tran_report = new TransactionReport();
		tran_report.setCreateDate(today);
		tran_report.setCreateTime(nowTime);
		tran_report.setMoney(withdraw.getMoney());
		tran_report.setStatus(PayType.WITHDRAW.toString());
		tran_report.setPayer(buyer.getPayer());
		tran_report.setWallet(buyer);
		transactionReportRepository.save(tran_report);
		return "Success";
	}
	
	public BaseRestApi exchangeWalletService(WalletDTO dealer) {
		LocalDate today = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Wallet buyer = walletRepository.findByPayer(dealer.getPayer());
		Wallet seller = walletRepository.findByPayer(dealer.getReceiver());
		Long ref = timestamp.getTime();
		System.out.println(ref);
		buyer.setMoney(buyer.getMoney() - dealer.getMoney());
		seller.setMoney(seller.getMoney() + dealer.getMoney());
		TransactionReport tran_report = new TransactionReport();
		tran_report.setCreateDate(today);
		tran_report.setCreateTime(nowTime);
		tran_report.setMoney(dealer.getMoney());
		tran_report.setNote(dealer.getNote());
		tran_report.setStatus(PayType.PAYER.toString());
		tran_report.setPayer(buyer.getPayer());
		tran_report.setReceiver(seller.getPayer());
		tran_report.setWallet(buyer);
		tran_report.setReferencetran(ref.toString());
		transactionReportRepository.save(tran_report);
		TransactionReport tran_report2 = new TransactionReport();
		tran_report2.setCreateDate(today);
		tran_report2.setCreateTime(nowTime);
		tran_report2.setMoney(dealer.getMoney());
		tran_report2.setStatus(PayType.RECEIVE.toString());
		tran_report2.setPayer(buyer.getPayer());
		tran_report2.setReceiver(seller.getPayer());
		tran_report2.setWallet(seller);
		tran_report2.setReferencetran(ref.toString());
		transactionReportRepository.save(tran_report2);
		BaseRestApi br = new BaseRestApi();
		BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
		Map<String, Object> model = new HashMap<>();
		model.put("payer", tran_report.getPayer());
		model.put("today", tran_report.getCreateDate());
		model.put("time", tran_report.getCreateTime());
		model.put("money", tran_report.getMoney());
		model.put("balance", buyer.getMoney());
		model.put("note", tran_report.getNote());
		br.setSuccess(true);
		baseResponse.setData(model);
		br.setResponse(baseResponse);
		return br;
	}
	
	public String newuserwallet(Wallet wallet) {
		Wallet wall = walletRepository.findByPayer(wallet.getPayer());
		if(wall!=null){
			return "This user has already been named.";			
		}
		walletRepository.save(wallet);
		return "success";
	}

	public WalletDTO getwalletuser(String username){
		Wallet wallet=walletRepository.findByPayer(username);
		WalletDTO walletdto = modelmapper.map(wallet, WalletDTO.class);
		return walletdto;			
	}

	public BaseRestApi checkuserwallet(WalletDTO walletDTO) {
	    BaseRestApi baseRestApi = new BaseRestApi();
	    BaseResponse< Map<String, Object>> baseResponse = new BaseResponse<>();
	    
	    Wallet payer = walletRepository.findByPayer(walletDTO.getPayer());
		if (payer == null) {
			baseRestApi.setSuccess(false);
			baseResponse.setErrorMessage("Payer not found");
			baseRestApi.setResponse(baseResponse);
			return baseRestApi;
		}
		Wallet receiver = walletRepository.findByPayer(walletDTO.getReceiver());
		if (receiver == null) {
			baseRestApi.setSuccess(false);
			baseResponse.setErrorMessage("Receiver not found");
			baseRestApi.setResponse(baseResponse);
			return baseRestApi;
		}
		if(receiver.equals(payer)){
			baseRestApi.setSuccess(false);
			baseResponse.setErrorMessage("Payer and Receiver is same!");
			baseRestApi.setResponse(baseResponse);
			return baseRestApi;
		}
		if(walletDTO.getMoney()<=0){
			baseRestApi.setSuccess(false);
			baseResponse.setErrorMessage("Amount must be greater than 0");
			baseRestApi.setResponse(baseResponse);
			return baseRestApi;
		}
		if(payer.getMoney()<=0||payer.getMoney()<walletDTO.getMoney()){
			baseRestApi.setSuccess(false);
			baseResponse.setErrorMessage("Your balance is not enough.");
			baseRestApi.setResponse(baseResponse);
			return baseRestApi;
		}
		baseRestApi.setSuccess(true);
		return baseRestApi;
	}


	public BaseRestApi calceltransaction(Long id,WalletDTO walletDTO) {
		 LocalDate today = LocalDate.now();
		 LocalTime nowTime = LocalTime.now();
		 BaseRestApi baseRestApi = new BaseRestApi();
		 BaseResponse< Map<String, Object>> baseResponse = new BaseResponse<>();
		 TransactionReport tr = transactionReportRepository.findByIdWallet(id);
		 TransactionReport tr2 = transactionReportRepository.findByIdWallet(tr.getPayer(), tr.getReferencetran());
		  Wallet payer = walletRepository.findByPayer(tr.getReceiver());
		  if (payer == null) {
				baseRestApi.setSuccess(false);
				baseResponse.setErrorMessage("Payer not found");
				baseRestApi.setResponse(baseResponse);
				return baseRestApi;
			}
		  Wallet receiver = walletRepository.findByPayer(tr.getPayer());
		  if (receiver == null) {
				baseRestApi.setSuccess(false);
				baseResponse.setErrorMessage("Receiver not found");
				baseRestApi.setResponse(baseResponse);
				return baseRestApi;
			}
		  if(payer.getMoney()<tr.getMoney()){
			  baseRestApi.setSuccess(false);
			  baseResponse.setErrorMessage("Money is not enough.");
			  baseRestApi.setResponse(baseResponse);
			  return baseRestApi;
		  }
		  payer.setMoney(payer.getMoney() - tr.getMoney());
		  receiver.setMoney(receiver.getMoney() + tr.getMoney());
			TransactionReport tran_report = new TransactionReport();
			tran_report.setReferenceId(id.toString());
			tran_report.setMoney(tr.getMoney());
			tran_report.setStatus(PayType.PAYER.toString());
			tran_report.setPayer(payer.getPayer());
			tran_report.setReceiver(receiver.getPayer());
			tran_report.setWallet(payer);
			tran_report.setEditBy(walletDTO.getUsernameAdmin());
			tran_report.setEditDate(today);
			tran_report.setEditTime(nowTime);
			transactionReportRepository.save(tran_report);
			TransactionReport tran_report2 = new TransactionReport();
			tran_report2.setMoney(tr.getMoney());
			tran_report2.setStatus(PayType.RECEIVE.toString());
			tran_report2.setPayer(payer.getPayer());
			tran_report2.setReceiver(receiver.getPayer());
			tran_report2.setWallet(receiver);
			tran_report2.setEditBy(walletDTO.getUsernameAdmin());
			tran_report2.setEditDate(today);
			tran_report2.setEditTime(nowTime);
			transactionReportRepository.save(tran_report2);
			tr.setStatus(PayType.CANCEL.toString());
			tr2.setStatus(PayType.CANCEL.toString());
			transactionReportRepository.save(tr);
			transactionReportRepository.save(tr2);
			baseRestApi.setSuccess(true);
      return baseRestApi;}
	
	public BaseRestApi changestatuswallet(WalletDTO walletDTO) {
		BaseRestApi baseRestApi = new BaseRestApi();
		BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
		Map<String, Object> model = new HashMap<>();
		return null;

		
	}
}
