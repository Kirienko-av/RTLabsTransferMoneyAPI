package ru.trial_assigment.money_transfer.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.trial_assigment.money_transfer.models.Balance;
import ru.trial_assigment.money_transfer.repositories.BalancesReposytory;

@Service
public class BalanceService {
	@Autowired
	BalancesReposytory balancesReposytory;
	
	public Iterable<Balance> getAll() {
		return balancesReposytory.findAll();	
	}
	
	public Optional<Balance> findBalance(long balanceId){
		return balancesReposytory.findById(balanceId);
	}
	
	public Iterable<Balance> findBalancesByAccount(long accountId){
		return balancesReposytory.findByAccount(accountId);
	}
	
	public Optional<Balance> findBalanceByAccount(long accountId, Date date){
		return balancesReposytory.findActualByAccount(accountId, date);
	}

}
