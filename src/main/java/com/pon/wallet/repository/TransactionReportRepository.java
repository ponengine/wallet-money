package com.pon.wallet.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pon.wallet.dto.TransactionReportDTO;
import com.pon.wallet.entity.TransactionReport;
import com.pon.wallet.entity.Wallet;
public interface TransactionReportRepository extends JpaRepository<TransactionReport, Long>, CrudRepository<TransactionReport, Long>{
//		@Modifying
//	    @Transactional
//	    @Query("delete from TransactionReport t where t.Payer = ?1")
//	    void deleteTransByUsername(String userName);
	
		@Query("select t from TransactionReport t where t.payer = ?1")
	    List<TransactionReport> findByPayer(String username);
		
		//Admin
		@Query("select t from TransactionReport t where  t.status = ?1")
		List<TransactionReport> findAllwithStatus(String status);
		@Query("select t from TransactionReport t where t.createDate = CURDATE()")
		List<TransactionReport> findByCreateDate();
		@Query("select t from TransactionReport t where t.createDate between ?1 and ?2 and t.status = ?3")
		List<TransactionReport> findByStartDateandEndDate(LocalDate startDate,LocalDate endDate,String status);
		@Query("select t from TransactionReport t where t.createDate between ?1 and ?2 ")
		List<TransactionReport> findByStartDateandEndDateandNostatus(LocalDate startDate,LocalDate endDate);
		
		//User
		@Query("select t from TransactionReport t where t.wallet = ?1 order by t.id desc")
		List<TransactionReport> findByWallet(Wallet wallet); //all
		@Query("select t from TransactionReport t where t.wallet = ?1 and t.status = ?2")
		List<TransactionReport> findByUserwithStatus(Wallet wallet,String status);// allwithstatus
		@Query("select t from TransactionReport t where t.wallet = ?1 and t.createDate = CURDATE()")
		List<TransactionReport> findByUserwithStatusAndDatetoDay(Wallet wallet);//today
		@Query("select t from TransactionReport t where t.wallet = ?1 and t.createDate between ?2 and ?3 ")
		List<TransactionReport> findByUserwitSearchAll(Wallet wallet,LocalDate startDate,LocalDate endDate);//searchnostatus
		@Query("select t from TransactionReport t where t.wallet = ?1 and t.createDate between ?2 and ?3 and t.status = ?4")
		List<TransactionReport> findByUserwithSearchAndStatus(Wallet wallet,LocalDate startDate,LocalDate endDate,String status);
}
