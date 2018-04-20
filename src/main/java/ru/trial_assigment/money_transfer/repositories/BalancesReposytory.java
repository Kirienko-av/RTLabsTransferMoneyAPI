package ru.trial_assigment.money_transfer.repositories;

import org.springframework.data.repository.CrudRepository;

import ru.trial_assigment.money_transfer.models.Balance;

public interface BalancesReposytory extends CrudRepository<Balance, Long> {

}
