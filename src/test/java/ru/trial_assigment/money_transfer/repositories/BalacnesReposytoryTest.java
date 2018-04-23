package ru.trial_assigment.money_transfer.repositories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.trial_assigment.money_transfer.models.Account;
import ru.trial_assigment.money_transfer.models.Balance;
import ru.trial_assigment.money_transfer.models.Transaction;

import java.util.Date;

/**
 * Created by kirie on 21.04.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BalacnesReposytoryTest {
    @Autowired
    BalancesReposytory balancesReposytory;
    @Autowired
    AccountsRepository accountsRepository;
    @Autowired
    TransactionsRepository transactionsRepository;

    @Before
    public void init(){
        accountsRepository.save(new Account("Ivan"));
        accountsRepository.save(new Account("Petr"));
        Transaction transaction = Transaction
                .newBuilder()
                .setFromAccount(accountsRepository.findById(2L).get())
                .setToAccount(accountsRepository.findById(1L).get())
                .setValue(10).build();
        if(transaction.getChild().isPresent()){
            transactionsRepository.save(transaction.getChild().get());
        }
        transactionsRepository.save(transaction);
        balancesReposytory.save(new Balance(transactionsRepository.findById(1L).get(), null, new Date()));

    }

    @Test
    public void testFindById()  {
        Assert.assertNotNull(balancesReposytory.findById(1L).get());
        Assert.assertEquals(balancesReposytory.findById(1L).get().getId(), 1L);
        Assert.assertEquals(balancesReposytory.findById(1L).get().getAccount().get().getName(), "Ivan");
        Assert.assertFalse(balancesReposytory.findById(300L).isPresent());
    }

    @Test
    public void testFindByAccount()  {
        Assert.assertNotNull(balancesReposytory.findByAccount(accountsRepository.findById(1L).get()));
        Assert.assertEquals(balancesReposytory.findByAccount(accountsRepository
                .findById(1L).get())
                .iterator().next()
                .getAccount().get().getName(), "Ivan");
        
        Assert.assertNotNull(balancesReposytory.findByAccount(1L));
        Assert.assertEquals(balancesReposytory.findByAccount(1L)
                .iterator().next()
                .getAccount().get().getName(), "Ivan");
    }
}
