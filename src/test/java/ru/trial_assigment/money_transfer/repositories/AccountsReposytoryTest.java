package ru.trial_assigment.money_transfer.repositories;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.trial_assigment.money_transfer.models.Account;


/**
 * Created by kirie on 21.04.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountsReposytoryTest {
    @Autowired
    AccountsRepository accountsRepository;

    @Before
    public void init(){
        accountsRepository.save(new Account("Ivan"));
        accountsRepository.save(new Account("Petr"));
    }


    @Test
    public void testFindById()  {
        Assert.assertNotNull(accountsRepository.findById(1L).get());
        Assert.assertEquals(accountsRepository.findById(1L).get().getId(), 1L);
        Assert.assertEquals(accountsRepository.findById(1L).get().getName(), "Ivan");
        Assert.assertFalse(accountsRepository.findById(300L).isPresent());
    }

    @Test
    public void testFindByName() {
        Assert.assertEquals(accountsRepository.findByName("Petr").iterator().next().getId(), 2L);
        Assert.assertEquals(accountsRepository.findByName("petr").iterator().next().getId(), 2L);
        Assert.assertEquals(accountsRepository.findByName("PeTr").iterator().next().getId(), 2L);
    }
}
