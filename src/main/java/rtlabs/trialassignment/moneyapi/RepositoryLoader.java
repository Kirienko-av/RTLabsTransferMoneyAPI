package rtlabs.trialassignment.moneyapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import rtlabs.trialassignment.moneyapi.Entitys.Account;
import rtlabs.trialassignment.moneyapi.LessThanZeroException;
import rtlabs.trialassignment.moneyapi.Repositorys.AccountsRepository;

/**
 * Created by kirie on 24.03.2018.
 */

@Component
public class RepositoryLoader implements ApplicationRunner {

    private AccountsRepository accountsRepository;

    @Autowired
    public RepositoryLoader(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public void run(ApplicationArguments args) throws LessThanZeroException {
        accountsRepository.save(new Account("Ivan", 0D));
        accountsRepository.save(new Account("Petr", 0D));
    }
}