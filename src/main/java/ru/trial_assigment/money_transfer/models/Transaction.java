package ru.trial_assigment.money_transfer.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Optional;

import javax.persistence.*;

/**
 * Created by kirie on 24.03.2018.
 */

@Entity
@Table(name="transactions")
public class Transaction {
	
	public static enum OPERATION {
		INCREASE(true), 
		REDUCE(false);
		private boolean operation;
		private OPERATION(boolean operation) {
			this.operation = operation;
		}
		public boolean toBoolean() {
			return operation;
		}
		public static OPERATION getOperation(boolean value) {
			return value?INCREASE:REDUCE;
		}
		
	}
	
	public static enum STATUS {
		CREATE, 
		SUCCSESS,
		ERROR;			
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "is_increase")
    private boolean operation;

    @Column(name = "value")
    private long value; //replace to money type    
    

    @Enumerated
    @Column(name = "status_id")
    private STATUS status; //create new

    @OneToOne()
    @JoinColumn(name = "transaction_id")
    private Transaction child;
    

    private Transaction() {
        // default constructor
    }   


    public long getId() {
        return id;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public Optional<Account> getAccount() {
    	if(account == null)
    		return Optional.empty();
        return Optional.of(account);
    }


    public long getValue() {
        return value;
    }

    public Optional<Transaction> getChild() {
        if(child == null)
    		return Optional.empty();
        return Optional.of(child);
    }

    public OPERATION getOperation() {
        return OPERATION.getOperation(operation);
    }
    
    public static Builder newBuilder() {
        return new Transaction().new Builder();
    }
    
    public class Builder {
    	private Optional<Account> fromAccount, toAccount;

    	private Builder() {
    		fromAccount = Optional.empty();
    		toAccount = Optional.empty();
    	}

    	public Builder  setValue(long value) throws IllegalArgumentException {
    		if (value < 0L)
    			throw new IllegalArgumentException("The value can not be less than zero");
    		Transaction.this.value = value;
    		return this;
    	}


    	public Builder setFromAccount(Account account) {
    		this.fromAccount = checkAccount(account);    		
    		return this;
    	}    	
    	
    	
    	public Builder setToAccount(Account account) {
    		toAccount = checkAccount(account);    		
    		return this;
    	}
    	
    	public Transaction build() throws IllegalArgumentException {
    		Transaction.this.status = STATUS.CREATE;
    		if (fromAccount.isPresent()) {
    			Transaction.this.operation = OPERATION.REDUCE.toBoolean();
    			Transaction.this.account = fromAccount.get();
    			if (toAccount.isPresent()) {
    				if(toAccount.get().equals(fromAccount.get()))
    					throw new IllegalArgumentException("The fromAccount and toAccount can not be equals");
    				Transaction.this.child = Transaction.newBuilder()
    						.setValue(Transaction.this.value)
    						.setToAccount(toAccount.get())
    						.build();
    			}
    		} else if(toAccount.isPresent()) {
    			Transaction.this.operation = OPERATION.INCREASE.toBoolean();
    			Transaction.this.account = toAccount.get();
    		}
    		else {
    			throw new IllegalArgumentException("There must be at least one value for fromAccount or toAccount");
			}
    		return Transaction.this;
    	}
    	
    	private Optional<Account> checkAccount(Account account){
    		if(account == null) {
    			return  Optional.empty();
    		} else {
    			return Optional.of(account);
    		}
    	}
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .append(getAccount().get().getId())
                .toHashCode();
    }


    @Override
    public boolean equals(final Object otherObj) {
        if ((otherObj == null) || !(otherObj instanceof Transaction)) {
            return false;
        }
        final Transaction other = (Transaction) otherObj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .append(getAccount().get().getId(), other.getAccount().get().getId())
                .isEquals();
    } 
    
    @Override
    public String toString() { 
        return "Id: '" + this.id + "', " +
        		"Account: '(" + this.account.toString() + ")', " +
        		"Operation: '" + OPERATION.getOperation(this.operation) + "', " +
        		"Value: '" + this.value + "', " + 
        		"Status: '" + this.status + "', " +
        		"Child Transaction: '(" + ((this.child==null)?"NULL":this.child.toString()) + ")'";
        		
    }

}




