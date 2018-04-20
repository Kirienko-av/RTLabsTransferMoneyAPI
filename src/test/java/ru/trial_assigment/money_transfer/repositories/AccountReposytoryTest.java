package ru.trial_assigment.money_transfer.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import ru.trial_assigment.money_transfer.models.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by kirie on 21.04.2018.
 */
@RunWith(SpringRunner.class)
public class AccountReposytoryTest {
    @Autowired
    AccountsRepository accountsRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Ignore
    @Test
    public void findByIdTest()  {
        accountsRepository.save(new Account("Ivan"));
        accountsRepository.save(new Account("Petr"));
        Account account = accountsRepository.findById(1L).get();
        assertThat(account.getId(), is(1L));
    }
}
