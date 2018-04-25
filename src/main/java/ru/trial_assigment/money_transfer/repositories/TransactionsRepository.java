package ru.trial_assigment.money_transfer.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.trial_assigment.money_transfer.models.Account;
import ru.trial_assigment.money_transfer.models.Transaction;

/**
 * Created by kirie on 24.03.2018.
 */
public interface TransactionsRepository extends CrudRepository<Transaction, Long> {

	@Query("from Transaction t where t.account=:account")
    public Iterable<Transaction> findByAccount(@Param("account") Account account);
    
    @Query("select t from Transaction t join t.account a where a.id=:account_id")
    public Iterable<Transaction> findByAccount(@Param("account_id") long accountId);
}
