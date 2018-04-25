package ru.trial_assigment.money_transfer.services;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.asm.Advice.Return;
import ru.trial_assigment.money_transfer.models.Balance;
import ru.trial_assigment.money_transfer.models.Transaction;
import ru.trial_assigment.money_transfer.repositories.BalancesReposytory;
import ru.trial_assigment.money_transfer.repositories.TransactionsRepository;

@Service
public class TransactionService {
	@Autowired
	TransactionsRepository transactionsRepository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BalancesReposytory balancesReposytory;
	
	private Transaction transaction;
	
	private static final ExecutorService executorService = Executors.newFixedThreadPool(100);	
	
	public Iterable<Transaction> getAll() {
		return transactionsRepository.findAll();	
	}
	
	public Optional<Transaction> findBalance(long transactionId){
		return transactionsRepository.findById(transactionId);
	}
	
	public Iterable<Transaction> findBalancesByAccount(long accountId){
		return transactionsRepository.findByAccount(accountId);
	}
	
	public Optional<Transaction> createTransaction(Long fromAccountId, Long toAccountId, Long value ) {
		Transaction.Builder builder = Transaction.newBuilder();
		if (fromAccountId != null)
			if (accountService.findAccount(fromAccountId).isPresent())
				builder = builder.setFromAccount(accountService.findAccount(fromAccountId).get());
		if (toAccountId != null)
			if (accountService.findAccount(toAccountId).isPresent())
				builder = builder.setToAccount(accountService.findAccount(toAccountId).get());
		if (value != null)
				builder = builder.setValue(value);
		transaction = transactionsRepository.save(builder.build());
		return Optional.of(transaction);
	}
	
	/*private Callable<Transaction> executeTransaction = () -> {
		if (transactionsRepository.findById(transaction.getId()).isPresent())
				transaction = transactionsRepository.findById(transaction.getId()).get();
		balancesReposytory.save(Balance.newBuilder(transaction)
				.setPreviousBalance(balancesReposytory.findActualByAccount(transaction.getAccount().get(), new Date()).get())
				.build());
		if(transaction.getChild().isPresent())
			balancesReposytory.save(Balance.newBuilder(transaction.getChild().get())
					.setPreviousBalance(balancesReposytory.findActualByAccount(transaction.getChild().get().getAccount().get(), new Date()).get())
					.build());
		transactionsRepository.save(transaction.getChild().get());
		return transactionsRepository.save(transaction);
	};*/

}
