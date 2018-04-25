package ru.trial_assigment.money_transfer.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.trial_assigment.money_transfer.models.Account;
import ru.trial_assigment.money_transfer.repositories.AccountsRepository;

@Service
public class AccountService {
	@Autowired
	AccountsRepository accountsRepository;
	
	private Optional<Account> account;
	
	public Optional<Account> changeName(long accountId, String name) {
		account = accountsRepository.findById(accountId);
		if (account.isPresent())
			account.get().setName(name);
		else
			return Optional.empty();
		return Optional.of(accountsRepository.save(account.get()));
	}
	
	public Optional<Account> findAccount(long accountId) {		
		return accountsRepository.findById(accountId);
	}
	
	public Iterable<Account> findAccount(String name) {
		return accountsRepository.findByName(name);	
	}
	
	public Iterable<Account> getAll() {
		return accountsRepository.findAll();	
	}

}
