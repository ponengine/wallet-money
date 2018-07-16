package com.pon.wallet.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.pon.wallet.domain.BaseRestApi;
import com.pon.wallet.dto.WalletDTO;
import com.pon.wallet.entity.Wallet;
import com.pon.wallet.service.WalletService;

@RestController
@RequestMapping("/api/walletpocket")
public class WalletController {
	@Autowired
	private WalletService walletService;
	@PostMapping("/newuser")
	public String newUser(@RequestBody Wallet wallet){
		return walletService.newuserwallet(wallet);
	}
	@PostMapping("/addwallet")
	public String addWallet(@RequestBody WalletDTO walletDTO){return walletService.addWalletService(walletDTO);}
	@PostMapping("/withdrawwallet")
	public String withdrawWallet(@RequestBody  WalletDTO withdraw){return walletService.withdrawWalletService(withdraw);}
	@PostMapping("/exchangewallet")
	public BaseRestApi exchangeWallet(@RequestBody  WalletDTO dealer){return walletService.exchangeWalletService(dealer);}
	@GetMapping("/walletget/{username}")
	public WalletDTO getwallet(@PathVariable("username") String username){return walletService.getwalletuser(username);}
	@PostMapping("/transferwallet")
	public BaseRestApi transferWallet(@RequestBody  WalletDTO dealer){return walletService.transferWalletService(dealer);}
	@PostMapping("/checkuserandwallet")
	public BaseRestApi checkUser(@RequestBody WalletDTO walletDTO){return walletService.checkuserwallet(walletDTO);}
//	@PostMapping("/changestatuswallet")
//	public BaseRestApi changeStatusWallet(@RequestBody WalletDTO walletDTO){return walletService.changestatuswallet(walletDTO);}
}
