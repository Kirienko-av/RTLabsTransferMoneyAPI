package ru.trial_assigment.money_transfer.models;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import ru.trial_assigment.money_transfer.models.Transaction.OPERATION;
import ru.trial_assigment.money_transfer.models.Transaction.STATUS;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
public class TransactionTest {
	private static Account account1 = new Account("test1");
	private static Account account2 = new Account("test2");
	
	@Test
	public void testTransactionNoAccounts() {
		try {
            Transaction.newBuilder().build();
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("There must be at least one value for fromAccount or toAccount"));
        }
	}
	
	@Test
	public void testTransactionValueLessZero() {
		try {
			Transaction.newBuilder().setToAccount(account1).setValue(-1L).build();
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("The value can not be less than zero"));
        }
	}
	
	@Test
	public void testTransactionAccountEcuals() {
		//account1 = account2
		try {
			Transaction.newBuilder().setToAccount(account1).setFromAccount(account2).build();
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("The fromAccount and toAccount can not be equals"));
        }
	}
	
	
	@Test
	public void testTransactionToString() {
            assertThat(Transaction.newBuilder()
            						.setToAccount(account1)
            						.setValue(100)
            						.build()
            						.toString(),            			
            is("Id: '0', " +
            		"Account: '(" + account1.toString() + ")', " +
            		"Operation: 'INCREASE', " +
            		"Value: '100', " + 
            		"Status: 'CREATE', " + 
            		"Child Transaction: '(NULL)'"));
	}
	
	@Test
	public void testTransaction()	{
		Transaction transaction1 = Transaction
									.newBuilder()
									.setValue(100)
									.setFromAccount(account1)
									.build();
		assertThat(transaction1.getAccount().get(), is(account1));
		assertThat(transaction1.getOperation(), is(OPERATION.REDUCE));
		assertThat(transaction1.getStatus(), is(STATUS.CREATE));
		assertThat(transaction1.getValue(), is(100L));
		transaction1.setStatus(STATUS.SUCCSESS);
		assertThat(transaction1.getStatus(), is(STATUS.SUCCSESS));
		
		Transaction transaction2 = Transaction
				.newBuilder()
				.setValue(100)
				.setToAccount(account2)
				.build();
		assertThat(transaction2.getAccount().get(), is(account2));
		assertThat(transaction2.getOperation(), is(OPERATION.INCREASE));
		assertThat(transaction2.getStatus(), is(STATUS.CREATE));
		assertThat(transaction2.getValue(), is(100L));
		transaction2.setStatus(STATUS.ERROR);
		assertThat(transaction2.getStatus(), is(STATUS.ERROR));												
	}

}
