package ru.trial_assigment.money_transfer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import ru.trial_assigment.money_transfer.models.Account;

/**
 * Created by kirie on 24.03.2018.
 */
public interface AccountsRepository extends CrudRepository<Account, Long> {
    @Query("from Account a where lower(a.name)=lower(:name)")
    public Iterable<Account> findByName(@Param("name") String name);
}
