package ru.trial_assigment.money_transfer.models;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class AccountTest {
	private static Account account1 = new Account("test1");
	private static Account account2 = new Account("test2");	
	
	@Test
	public void testAccountNullName() {
		try {
            new Account(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("The Name not be NULL"));
        }
	}
	
	@Test
	public void testAccount() {            
            assertThat(account1.getName(), is("test1"));
            account1.setName("renametest1");
            assertThat(account1.getName(), is("renametest1"));
        
	}	
	
	@Test
	public void testAccountToString() {
            assertThat(account1.toString(), is("Id: '"+ account1.getId() +
            									"', Name: '" + account1.getName() +
												"', Ballance: '(NULL)'"));
	}
	
	@Test
	public void testAccountEquals() {
			//default id is 0 then account = account2
            assertThat(account1.equals(account2), is(true));
            assertThat(account1.equals(null), is(false));
            Account nullAccount = null;
            assertThat(nullAccount == null, is(true));
	}
	

}
