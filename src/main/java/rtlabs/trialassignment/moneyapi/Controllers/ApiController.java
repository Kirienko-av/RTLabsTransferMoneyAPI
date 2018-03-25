package rtlabs.trialassignment.moneyapi.Controllers;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import rtlabs.trialassignment.moneyapi.Entitys.Account;
import rtlabs.trialassignment.moneyapi.Entitys.Transaction;
import rtlabs.trialassignment.moneyapi.LessThanZeroException;
import rtlabs.trialassignment.moneyapi.Repositorys.AccountsRepository;
import rtlabs.trialassignment.moneyapi.Repositorys.TransactionsRepository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kirie on 24.03.2018.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final AccountsRepository accountsRepository;
    private final TransactionsRepository transactionsRepository;

    private static final ExecutorService ex = Executors.newFixedThreadPool(100);

    public ApiController(AccountsRepository accountsRepository, TransactionsRepository transactionsRepository) {
        this.accountsRepository = accountsRepository;
        this.transactionsRepository = transactionsRepository;
    }


    /*@RequestMapping(value = "/accounts", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Iterable<Account> getAccounts() {
        return accountsRepository.findAll();
    }*/

    @RequestMapping(value = "/accounts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAccounts() {
        return  new ResponseEntity<Object>(accountsRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAccountById(@RequestParam("id") Long id) {
        if(id == null)
            return new ResponseEntity<Object> (HttpStatus.BAD_REQUEST);
        Optional<Account> acc = accountsRepository.findById(id);
        if(!acc.isPresent()) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("error", "Account " + id + " not found");
            return new ResponseEntity<Object>(jsonObj, HttpStatus.OK);
        }
        return new ResponseEntity<Object>(accountsRepository.findById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getTransactions() {
        return new ResponseEntity<Object>(transactionsRepository.findAll(), HttpStatus.OK);
    }


    @RequestMapping(value = "/transaction", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody DeferredResult<ResponseEntity<Object>> transactById(@RequestParam(name = "from_account_id", required=false) Long from_account_id,
                                             @RequestParam(name = "to_account_id", required=false) Long to_account_id,
                                             @RequestParam(name = "value", defaultValue = "0") Double value) {
        DeferredResult<ResponseEntity<Object>> dr = new DeferredResult<>();
        JSONObject jsonObj = new JSONObject();

        CompletableFuture.supplyAsync(() -> {
            Transaction trFrom = null, trTo = null;
            if ((from_account_id == null && to_account_id == null) || to_account_id == from_account_id) {
                jsonObj.put("error", "One of the fields (from_account_id, to_account_id) should not be empty and the values should not be equal");
                return new ResponseEntity<Object>(jsonObj, HttpStatus.OK);
            }
            if (value < 0) {
                jsonObj.put("error", "The value can not be less than zero");
                return new ResponseEntity<Object>(jsonObj, HttpStatus.OK);
            }
            try {
                trFrom = execTrnc(from_account_id, value, false, null);
                trTo = execTrnc(to_account_id, value, true, trFrom);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<Object>(trTo == null ? trFrom : trTo, HttpStatus.OK) ;

        }, ex).thenAccept((ResponseEntity<Object> re) -> {
            dr.setResult(re);
        });

        return dr;
    }

    private Transaction execTrnc(Long account_id, Double value, Boolean increase, Transaction parent) throws InterruptedException {

        Transaction trn = null;
        Optional<Account> ao = null;
        for (int i = 0; i < 5; i++) {
            try {
                if (account_id != null) {
                    ao = accountsRepository.findById(account_id);
                }

                if (ao != null && !ao.isPresent()) {
                    return null;
                } else if (ao != null && ao.isPresent()) {
                    if (trn == null)
                        trn = transactionsRepository.save(new Transaction(ao.get(), value, increase, parent));
                    else
                        trn = transactionsRepository.save(trn);
                    trn.execute();
                    accountsRepository.save(ao.get());
                    trn = transactionsRepository.save(trn);
                    break;
                }
            } catch (LessThanZeroException e) {
                trn.setStatus(3);
                transactionsRepository.save(trn);
                Thread.sleep(2000L);
                continue;
            }
        }
        return trn;
    }
}

