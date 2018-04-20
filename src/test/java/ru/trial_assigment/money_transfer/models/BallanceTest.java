package ru.trial_assigment.money_transfer.models;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;

@RunWith(SpringRunner.class)
public class BallanceTest {
	private static Account account = new Account("test");	
	private static Transaction toAccount = Transaction.newBuilder().setToAccount(account).setValue(50).build();
	private static Transaction fromAccount = Transaction.newBuilder().setFromAccount(account).setValue(50).build();
	
	@Test
	public void testBallanceNullTransaction() {
		try {
            new Balance(null, null, new Date());
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("The arguments not be null value"));
        }
	}
	
	@Test
	public void testBallanceNullFromDate() {
		try {
            new Balance(toAccount, null, (Date)null, new Date());
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("The arguments not be null value"));
        }
	}
	
	@Test
	public void testBallanceNullToDate() {
		try {
            new Balance(fromAccount, null, new Date(), null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("The arguments not be null value"));
        }
	}
	
	@Test
	public void testBallanceLessZero() {
		try {
            new Balance(fromAccount, null, new Date());
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("The ballance can not be less than zero"));
        }
	}
	
	@Test
	public void testBallance() {
		Balance ballance = new Balance(toAccount, null, new Date(1000000000));
		assertThat(ballance.getAccount().get(), is(toAccount.getAccount().get()));
		assertThat(ballance.getAccount().get().getId(), is(toAccount.getAccount().get().getId()));
		assertThat(ballance.getAccount().get().getName(), is(toAccount.getAccount().get().getName()));
		assertThat(ballance.getballance(), is(50L));
		assertThat(ballance.getFromDate(), is(new Date(1000000000)));
		assertThat(ballance.getToDate(), is(new Date(Long.MAX_VALUE)));
		assertThat(ballance.getTransaction().get(), is(toAccount));
		
		Balance ballance2 = new Balance(fromAccount, ballance, new Date(1000000000));
		assertThat(ballance2.getAccount().get(), is(fromAccount.getAccount().get()));
		assertThat(ballance2.getAccount().get().getId(), is(fromAccount.getAccount().get().getId()));
		assertThat(ballance2.getAccount().get().getName(), is(fromAccount.getAccount().get().getName()));
		assertThat(ballance2.getballance(), is(0L));
		assertThat(ballance2.getFromDate(), is(new Date(1000000000)));
		assertThat(ballance2.getToDate(), is(new Date(Long.MAX_VALUE)));
		assertThat(ballance2.getTransaction().get(), is(fromAccount));
	}
	
	@Test
	public void testBallanceToString() {
		Balance ballance = new Balance(toAccount, null, new Date(1000000000));		
         assertThat(ballance.toString(), is("Id: '0', Account: '(Id: '0', Name: 'test')', Ballance: 50', " + 
        		 							"Transaction: (Id: '0', Account: '(Id: '0', Name: 'test')', " + 
        		 							"Operation: 'INCREASE', Value: '50', Status: 'CREATE', " + 
        		 							"Child Transaction: '(NULL)')', FromDate: Mon Jan 12 23:46:40 VLAT 1970', " +
        		 							"ToDate: Sun Aug 17 17:12:55 VLAT 292278994"));
	}

}




