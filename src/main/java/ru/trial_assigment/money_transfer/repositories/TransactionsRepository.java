package ru.trial_assigment.money_transfer.repositories;

import org.springframework.data.repository.CrudRepository;

import ru.trial_assigment.money_transfer.models.Transaction;

/**
 * Created by kirie on 24.03.2018.
 */
public interface TransactionsRepository extends CrudRepository<Transaction, Long> {

    //@Query( "select o from MyObject o where inventoryId in :ids" )
    //List<Long> findById(@Param("id") Long id);
}
