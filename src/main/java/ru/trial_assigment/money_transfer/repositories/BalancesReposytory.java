package ru.trial_assigment.money_transfer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import ru.trial_assigment.money_transfer.models.Account;
import ru.trial_assigment.money_transfer.models.Balance;

import java.util.Set;

public interface BalancesReposytory extends CrudRepository<Balance, Long> {
    @Query("from Balance b where b.account=:account")
    public Set<Balance> findByAccount(@Param("account") Account account);
    
    @Query("select b from Balance b join b.account a where a.id=:account_id")
    public Set<Balance> findByAccount(@Param("account_id") long accountId);

    /*@Query("select b from Balance b where b.transaction_id=:transaction_id")
    public Optional<Balance> findByTransactionId(@Param("transaction_id") Long transactionId);

    @Query("select a from Account a where lower(a.name)=lower(:name)")
    public Iterable<Account> findByName(@Param("name") String name);

    @Query("select a from Account a where lower(a.name)=lower(:name)")
    public Iterable<Account> findByName(@Param("name") String name);*/

}
