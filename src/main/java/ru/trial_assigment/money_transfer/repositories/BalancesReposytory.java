package ru.trial_assigment.money_transfer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import ru.trial_assigment.money_transfer.models.Account;
import ru.trial_assigment.money_transfer.models.Balance;
import ru.trial_assigment.money_transfer.models.Transaction;

import java.util.Date;
import java.util.Optional;
import java.util.Set;



public interface BalancesReposytory extends CrudRepository<Balance, Long> {
    @Query("from Balance b where b.account=:account")
    public Set<Balance> findByAccount(@Param("account") Account account);
    
    @Query("select b from Balance b join b.account a where a.id=:account_id")
    public Set<Balance> findByAccount(@Param("account_id") long accountId); 

    @Query("from Balance b where b.account=:account and :date between b.fromDate AND b.toDate")
    public Optional<Balance> findActualByAccount(
    		@Param("account") 
    		Account account,
    		@Param("date")
    		Date date);
    
    @Query("select b from Balance b join b.account a where a.id=:account_id and :date between b.fromDate AND b.toDate")
    public Optional<Balance> findActualByAccount(
    		@Param("account_id") 
    		long accountId,
    		@Param("date")
    		Date date);
    
    @Query("from Balance b where b.transaction=:transaction")
    public Optional<Balance> findByTransaction(@Param("transaction") Transaction transaction);
    
    @Query("select b from Balance b join b.transaction t where t.id=:transaction_id")
    public Optional<Balance> findByTransaction(@Param("transaction_id") long transactionId);

}
