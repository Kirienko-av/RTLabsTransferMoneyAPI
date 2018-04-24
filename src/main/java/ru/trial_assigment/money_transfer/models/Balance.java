package ru.trial_assigment.money_transfer.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ru.trial_assigment.money_transfer.models.Transaction.OPERATION;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.persistence.*;

/**
 * Created by kirie on 24.03.2018.
 */

@Entity
@Table(name="balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;
    
    @OneToOne()
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    
    @Column(name = "ballance")
    private long balance;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "from_date")    
    private Date fromDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "to_date")
    private Date toDate;

    @SuppressWarnings("unused")
	private Balance() {

    }

    public Balance(Transaction transaction, Balance previousBalance, Date fromDate, Date toDate) throws IllegalArgumentException, ParseException {
		if(transaction == null || !transaction.getAccount().isPresent() || fromDate == null)
			throw new IllegalArgumentException("The arguments not be null value");
		if (previousBalance != null && !previousBalance.getAccount().get().equals(transaction.getAccount().get()))
			throw new IllegalArgumentException("The previous ballance account not equal this");
    	this.account = transaction.getAccount().get();
		this.transaction = transaction;
		this.fromDate = fromDate;
		this.setToDate(toDate);
		if (previousBalance == null
				&& (transaction.getOperation() == OPERATION.INCREASE || transaction.getValue() == 0L))
			this.balance = transaction.getValue();
		else if (transaction.getOperation() == OPERATION.INCREASE) {
			this.balance = previousBalance.getBallance() + transaction.getValue();
			previousBalance.setToDate(fromDate);
		}
		else if(previousBalance != null
				&& transaction.getOperation() == OPERATION.REDUCE
				&& previousBalance.getBallance() - transaction.getValue() >= 0L) {
			this.balance = previousBalance.getBallance() - transaction.getValue();
			previousBalance.setToDate(new Date(fromDate.getTime() - 1L));
		} else {
			throw new IllegalArgumentException("The ballance can not be less than zero");	
		}
	}    
   
    
    public Balance(Transaction transaction, Balance previousBalance, Date fromDate) throws IllegalArgumentException, ParseException {
		this(transaction, previousBalance, fromDate, null);
	}


	public long getId() {
        return id;
    }

    public Optional<Account> getAccount() {
    	if(account == null)
    		return Optional.empty();
        return Optional.of(account);
    }
    
    public Optional<Transaction> getTransaction() {
        if(transaction == null)
    		return Optional.empty();
        return Optional.of(transaction);
    }
    
    public long getBallance() { 
        return balance;
    }    
    

    public Date getFromDate() {
		return fromDate;
	}
	

	public Date getToDate() {
		return toDate;
	}
	
	 public void setToDate(Date toDate) throws ParseException {
		if (toDate == null)
			this.toDate = new SimpleDateFormat("yyyy-MM-dd").parse("2999-12-31");
		else
			this.toDate = toDate;
	}


	@Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }


    @Override
    public boolean equals(final Object otherObj) {
        if ((otherObj == null) || !(otherObj instanceof Account)) {
            return false;
        }
        final Account other = (Account) otherObj;
        return new EqualsBuilder().append(getId(), other.getId())
                  .isEquals();
    }
    
    @Override
    public String toString() { 
        return "Id: '" + this.id +
        		"', Account: '(" + this.account.toString() +
        		")', Ballance: " + this.balance +
        		"', Transaction: (" + this.transaction.toString() +
        		")', FromDate: " + fromDate +
        		"', ToDate: " + this.toDate;
    }
}
