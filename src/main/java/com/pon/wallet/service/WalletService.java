package com.pon.wallet.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
import com.pon.wallet.dto.Dealing;
import com.pon.wallet.dto.WalletDTO;
import com.pon.wallet.entity.TransactionReport;
import com.pon.wallet.entity.Wallet;
import com.pon.wallet.repository.TransactionReportRepository;
import com.pon.wallet.repository.WalletRepository;

@Service
public class WalletService {

	LocalDate today = LocalDate.now();
	LocalTime nowTime = LocalTime.now();
	ModelMapper modelmapper = new ModelMapper();
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private TransactionReportRepository transactionReportRepository;
	@Autowired
	private Environment env;
	public String addWalletService(Dealing addwallet) {
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(addwallet.getPrice()<=0){
			return "Amount must be greater than 0";
		}
		Wallet wallet = walletRepository.findByUsernameBuyer(addwallet.getUsernameBuyer());
		if (wallet == null) {
			return "User not found";
		}
		String uri = env.getProperty("uri.mainwallet")+"/mainwallet/getmainwallet";	
		RestTemplate rt = new RestTemplate();
		int response = rt.getForObject( uri , int.class );
		if(response<addwallet.getPrice()){
			return "Not enough money for service.";
		}
		response=response-addwallet.getPrice();
		HttpEntity<String> entity = new HttpEntity<String>(Integer.toString(response) ,headers);
		String uri2 = env.getProperty("uri.mainwallet")+"/mainwallet/getmainwallet";
		JSONObject obj = new JSONObject();
		obj.put("money", response);		
		String status = rt.postForObject(uri2, obj.toString(), String.class);
		wallet.setWalletMoney(wallet.getWalletMoney() + addwallet.getPrice());
		TransactionReport tran_report = new TransactionReport();
		tran_report.setCreateDate(today);
		tran_report.setCreateTime(nowTime);
		tran_report.setMoneyWallet(addwallet.getPrice());
		tran_report.setStatus(PayType.ADD.toString());
		tran_report.setUsernameBuyer(wallet.getUsernameBuyer());
		tran_report.setWallet(wallet);
		transactionReportRepository.save(tran_report);
		return "Success";
	}
	
	public String withdrawWalletService(Dealing withdraw) {
		Wallet buyer = walletRepository.findByUsernameBuyer(withdraw.getUsernameBuyer());
		if(withdraw.getPrice()<=0){
			return "Amount must be greater than 0";
		}
		if (buyer == null) {
			return "User not found.";
		}
		if (!(buyer.getWalletMoney() >= withdraw.getPrice()) ) {
			return "Wallet not enough.";
		}
		buyer.setWalletMoney(buyer.getWalletMoney() - withdraw.getPrice());
		TransactionReport tran_report = new TransactionReport();
		tran_report.setCreateDate(today);
		tran_report.setCreateTime(nowTime);
		tran_report.setMoneyWallet(withdraw.getPrice());
		tran_report.setStatus(PayType.WITHDRAW.toString());
		tran_report.setUsernameBuyer(buyer.getUsernameBuyer());
		tran_report.setWallet(buyer);
		transactionReportRepository.save(tran_report);
		return "Success";
	}
	
	public String exchangeWalletService(Dealing dealer) {
		if(dealer.getPrice()<=0){
			return "Amount must be greater than 0";
		}
		Wallet buyer = walletRepository.findByUsernameBuyer(dealer.getUsernameBuyer());
		if (buyer == null) {
			return "User not found";
		}
		Wallet seller = walletRepository.findByUsernameBuyer(dealer.getUsernameSeller());
		if (seller == null) {
			return "User not found";
		}
		if(buyer.getWalletMoney()<=0||buyer.getWalletMoney()<dealer.getPrice()){
			return "Your balance is not enough.";
		}
		if(buyer.equals(seller)){
			return "Same user";
		}
		buyer.setWalletMoney(buyer.getWalletMoney() - dealer.getPrice());
		seller.setWalletMoney(seller.getWalletMoney() + dealer.getPrice());
		TransactionReport tran_report = new TransactionReport();
		tran_report.setCreateDate(today);
		tran_report.setCreateTime(nowTime);
		tran_report.setMoneyWallet(dealer.getPrice());
		tran_report.setStatus(PayType.SPEND.toString());
		tran_report.setUsernameBuyer(buyer.getUsernameBuyer());
		tran_report.setUsernameSeller(seller.getUsernameBuyer());
		tran_report.setWallet(buyer);
		transactionReportRepository.save(tran_report);
		TransactionReport tran_report2 = new TransactionReport();
		tran_report2.setCreateDate(today);
		tran_report2.setCreateTime(nowTime);
		tran_report2.setMoneyWallet(dealer.getPrice());
		tran_report2.setStatus(PayType.RECEIVE.toString());
		tran_report2.setUsernameBuyer(buyer.getUsernameBuyer());
		tran_report2.setUsernameSeller(seller.getUsernameBuyer());
		tran_report2.setWallet(seller);
		transactionReportRepository.save(tran_report2);
		return "Success";
	}
	
	public String newuserwallet(Wallet wallet) {
		Wallet wall = walletRepository.findByUsernameBuyer(wallet.getUsernameBuyer());
		if(wall!=null){
			return "This user has already been named.";			
		}
		walletRepository.save(wallet);
		return "success";
	}

	public WalletDTO getwalletuser(String username){
		Wallet wallet=walletRepository.findByUsernameBuyer(username);
		WalletDTO walletdto = modelmapper.map(wallet, WalletDTO.class);
		return walletdto;			
	}

}
