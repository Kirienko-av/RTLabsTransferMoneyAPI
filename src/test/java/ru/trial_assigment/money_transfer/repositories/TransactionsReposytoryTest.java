package ru.trial_assigment.money_transfer.repositories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import ru.trial_assigment.money_transfer.models.Account;
import ru.trial_assigment.money_transfer.models.Balance;
import ru.trial_assigment.money_transfer.models.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionsReposytoryTest {
	@Autowired
    BalancesReposytory balancesReposytory;
    @Autowired
    AccountsRepository accountsRepository;
    @Autowired
    TransactionsRepository transactionsRepository;
    
    private boolean isSetUp = false;

    @Before
    public void setUp(){
    	if (isSetUp)
    		return;
    	accountsRepository.save(new Account("Ivan"));
        Transaction transaction = Transaction
                .newBuilder()
                .setToAccount(accountsRepository.findById(1L).get())
                .setValue(10).build();
        if(transaction.getChild().isPresent()){
            transactionsRepository.save(transaction.getChild().get());
        }
        transactionsRepository.save(transaction);
        try {
			balancesReposytory.save(Balance.newBuilder(transaction).build());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        isSetUp = true;

    }    
    

    @Test
    public void testFindById()  {
        Assert.assertNotNull(transactionsRepository.findById(1L).get());
        Assert.assertEquals(transactionsRepository.findById(1L).get().getId(), 1L);
        Assert.assertEquals(transactionsRepository.findById(1L).get().getAccount().get().getName(), "Ivan");
        Assert.assertFalse(transactionsRepository.findById(300L).isPresent());
    }

    @Test
    public void testFindByAccount()  {
        Assert.assertNotNull(transactionsRepository.findByAccount(accountsRepository.findById(1L).get()));
        Assert.assertEquals(transactionsRepository.findByAccount(accountsRepository
                .findById(1L).get())
                .iterator().next()
                .getAccount().get().getName(), "Ivan");
        
        Assert.assertNotNull(transactionsRepository.findByAccount(1L));
        Assert.assertEquals(transactionsRepository.findByAccount(1L)
                .iterator().next()
                .getAccount().get().getName(), "Ivan");
    }   
   

}
