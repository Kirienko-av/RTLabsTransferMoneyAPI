package ru.trial_assigment.money_transfer.models;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BallanceTest {
	private static Account account = new Account("test");
	private static Transaction toAccount = Transaction.newBuilder().setToAccount(account).setValue(50).build();
	private static Transaction fromAccount = Transaction.newBuilder().setFromAccount(account).setValue(50).build();
	
	@Test
	public void testBallanceNullTransaction() {
		try {
            new Balance(null, null, new Date());
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "The arguments not be null value");
        }
	}
	
	@Test
	public void testBallanceNullFromDate() {
		try {
            new Balance(toAccount, null, (Date)null, new Date());
			Assert.fail();
        } catch (IllegalArgumentException e) {
			Assert.assertEquals(e.getMessage(), "The arguments not be null value");
        }
	}
	
	@Test
	public void testBallanceNullToDate() {
		try {
            new Balance(fromAccount, null, new Date(), null);
			Assert.fail();
        } catch (IllegalArgumentException e) {
			Assert.assertEquals(e.getMessage(), "The arguments not be null value");
        }
	}
	
	@Test
	public void testBallanceLessZero() {
		try {
            new Balance(fromAccount, null, new Date());
			Assert.fail();
        } catch (IllegalArgumentException e) {
			Assert.assertEquals(e.getMessage(), "The ballance can not be less than zero");
        }
	}
	
	@Test
	public void testBallance() {
		Balance ballance = new Balance(toAccount, null, new Date(1000000000));
		Assert.assertEquals(ballance.getAccount().get(), toAccount.getAccount().get());
		Assert.assertEquals(ballance.getAccount().get().getId(), toAccount.getAccount().get().getId());
		Assert.assertEquals(ballance.getAccount().get().getName(), toAccount.getAccount().get().getName());
		Assert.assertEquals(ballance.getballance(), 50L);
		Assert.assertEquals(ballance.getFromDate(), new Date(1000000000));
		Assert.assertEquals(ballance.getToDate(), new Date(Long.MAX_VALUE));
		Assert.assertEquals(ballance.getTransaction().get(), toAccount);
		
		Balance ballance2 = new Balance(fromAccount, ballance, new Date(1000000000));
		Assert.assertEquals(ballance2.getAccount().get(), fromAccount.getAccount().get());
		Assert.assertEquals(ballance2.getAccount().get().getId(), fromAccount.getAccount().get().getId());
		Assert.assertEquals(ballance2.getAccount().get().getName(), fromAccount.getAccount().get().getName());
		Assert.assertEquals(ballance2.getballance(), 0L);
		Assert.assertEquals(ballance2.getFromDate(), new Date(1000000000));
		Assert.assertEquals(ballance2.getToDate(), new Date(Long.MAX_VALUE));
		Assert.assertEquals(ballance2.getTransaction().get(), fromAccount);
	}
	
	@Test
	public void testBallanceToString() {
		Balance ballance = new Balance(toAccount, null, new Date(1000000000));
		Assert.assertEquals(ballance.toString(), "Id: '0', Account: '(Id: '0', Name: 'test')', Ballance: 50', " +
        		 							"Transaction: (Id: '0', Account: '(Id: '0', Name: 'test')', " +
        		 							"Operation: 'INCREASE', Value: '50', Status: 'CREATE', " + 
        		 							"Child Transaction: '(NULL)')', FromDate: Mon Jan 12 23:46:40 VLAT 1970', " +
        		 							"ToDate: Sun Aug 17 17:12:55 VLAT 292278994");
	}

}




