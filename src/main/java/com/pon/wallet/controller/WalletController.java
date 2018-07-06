package com.pon.wallet.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.pon.wallet.dto.Dealing;
import com.pon.wallet.dto.WalletDTO;
import com.pon.wallet.entity.Wallet;
import com.pon.wallet.service.WalletService;

@RestController
@RequestMapping("/managewallet")
public class WalletController {
	@Autowired
	private WalletService walletService;
	@PostMapping("/newuser")
	public String newUser(@RequestBody Wallet wallet){
		return walletService.newuserwallet(wallet);
	}
	@PostMapping("/addwallet")
	public String addWallet(@RequestBody Dealing addwallet){return walletService.addWalletService(addwallet);}
	@PostMapping("/withdrawwallet")
	public String withdrawWallet(@RequestBody Dealing withdraw){return walletService.withdrawWalletService(withdraw);}
	@PostMapping("/exchangewallet")
	public String exchangeWallet(@RequestBody Dealing dealer){return walletService.exchangeWalletService(dealer);}
	@GetMapping("/walletget/{username}")
	public WalletDTO getwallet(@PathVariable("username") String username){return walletService.getwalletuser(username);}
}
