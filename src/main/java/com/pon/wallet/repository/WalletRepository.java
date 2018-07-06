package com.pon.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pon.wallet.entity.Wallet;

public interface WalletRepository extends CrudRepository<Wallet,Long>{
	
	Wallet findByUsernameBuyer(String usernameBuyer);



}
