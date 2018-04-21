package ru.trial_assigment.money_transfer.models;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ru.trial_assigment.money_transfer.models.Transaction.OPERATION;
import ru.trial_assigment.money_transfer.models.Transaction.STATUS;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {
	private static Account account1 = new Account("test1");
	private static Account account2 = new Account("test2");
	
	@Test
	public void testTransactionNoAccounts() {
		try {
            Transaction.newBuilder().build();
            Assert.fail();
        } catch (IllegalArgumentException e) {
			Assert.assertEquals(e.getMessage(), "There must be at least one value for fromAccount or toAccount");
        }
	}
	
	@Test
	public void testTransactionValueLessZero() {
		try {
			Transaction.newBuilder().setToAccount(account1).setValue(-1L).build();
			Assert.fail();
        } catch (IllegalArgumentException e) {
			Assert.assertEquals(e.getMessage(), "The value can not be less than zero");
        }
	}
	
	@Test
	public void testTransactionAccountEcuals() {
		//account1 = account2
		try {
			Transaction.newBuilder().setToAccount(account1).setFromAccount(account2).build();
			Assert.fail();
        } catch (IllegalArgumentException e) {
			Assert.assertEquals(e.getMessage(), "The fromAccount and toAccount can not be equals");
        }
	}
	
	
	@Test
	public void testTransactionToString() {
		Assert.assertEquals(Transaction.newBuilder()
            						.setToAccount(account1)
            						.setValue(100)
            						.build()
            						.toString(),            			
            "Id: '0', " +
            		"Account: '(" + account1.toString() + ")', " +
            		"Operation: 'INCREASE', " +
            		"Value: '100', " + 
            		"Status: 'CREATE', " + 
            		"Child Transaction: '(NULL)'");
	}
	
	@Test
	public void testTransaction()	{
		Transaction transaction1 = Transaction
									.newBuilder()
									.setValue(100)
									.setFromAccount(account1)
									.build();
		Assert.assertEquals(transaction1.getAccount().get(), account1);
		Assert.assertEquals(transaction1.getOperation(), OPERATION.REDUCE);
		Assert.assertEquals(transaction1.getStatus(), STATUS.CREATE);
		Assert.assertEquals(transaction1.getValue(), 100L);
		transaction1.setStatus(STATUS.SUCCSESS);
		Assert.assertEquals(transaction1.getStatus(), STATUS.SUCCSESS);
		
		Transaction transaction2 = Transaction
				.newBuilder()
				.setValue(100)
				.setToAccount(account2)
				.build();
		Assert.assertEquals(transaction2.getAccount().get(), account2);
		Assert.assertEquals(transaction2.getOperation(), OPERATION.INCREASE);
		Assert.assertEquals(transaction2.getStatus(), STATUS.CREATE);
		Assert.assertEquals(transaction2.getValue(), 100L);
		transaction2.setStatus(STATUS.ERROR);
		Assert.assertEquals(transaction2.getStatus(), STATUS.ERROR);
	}

}
