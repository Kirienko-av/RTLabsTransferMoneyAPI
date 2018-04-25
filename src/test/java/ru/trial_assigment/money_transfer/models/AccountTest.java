package ru.trial_assigment.money_transfer.models;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountTest {
	private static Account account1 = new Account("test1");
	private static Account account2 = new Account("test2");
	
	@Test
	public void testAccountNullName() {
		try {
            new Account(null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "The Name not be NULL");
        }
	}
	
	@Test
	public void testAccount() {
		Assert.assertEquals(account1.getName(), "test1");
        account1.setName("renametest1");
		Assert.assertEquals(account1.getName(),"renametest1");
        
	}	
	
	@Test
	public void testAccountToString() {
		Assert.assertEquals(account1.toString(), "Id: '"+ account1.getId() +
            									"', Name: '" + account1.getName() + "'");
	}
	
	@Test
	public void testAccountEquals() {
		//default id is 0 then account = account2
		Assert.assertTrue(account1.equals(account2));
		Assert.assertFalse(account1.equals(null));
		Account nullAccount = null;
		Assert.assertNull(nullAccount);
	}
	

}
