package ru.trial_assigment.money_transfer.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public static Date MAX_DATE;

    static {
        try {
            MAX_DATE = new SimpleDateFormat("yyyy-MM-dd").parse("2999-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Balance() {

    }


    public long getId() {
        return id;
    }

    public Optional<Account> getAccount() {
        if (account == null)
            return Optional.empty();
        return Optional.of(account);
    }

    public Optional<Transaction> getTransaction() {
        if (transaction == null)
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


    public void setToDate(Date toDate) {
        if (toDate == null
                || toDate.getTime() > MAX_DATE.getTime())
            this.toDate = MAX_DATE;
        else if (fromDate.getTime() > toDate.getTime())
            throw new IllegalArgumentException("toDate can not be less than fromDate");
        else
            this.toDate = toDate;
    }

    public static Builder newBuilder(Transaction transaction) {
        return new Balance().new Builder(transaction);
    }

    public class Builder {

        private Optional<Balance> previousBalance;

        private Builder(Transaction transaction) {
            if (transaction == null || !transaction.getAccount().isPresent())
                throw new IllegalArgumentException("The arguments not be null value");
            if(transaction.getStatus() != Transaction.STATUS.CREATE)
                throw new IllegalArgumentException("The transaction must have the status CREATE");
            Balance.this.account = transaction.getAccount().get();
            Balance.this.transaction = transaction;
            Balance.this.fromDate = new Date();
            Balance.this.setToDate(Balance.MAX_DATE);
            this.previousBalance = Optional.empty();
        }

        public Builder setFromDate(Date fromDate) {
        	//TO-DO Реализовать возможность вклиниваннияю
            if (fromDate == null)
                return this;
            if (fromDate.getTime() > Balance.MAX_DATE.getTime())
                throw new IllegalArgumentException("fromDate can not be more than 2999-12-31");
            Balance.this.fromDate = fromDate;
            return this;
        }

        public Builder setToDate(Date toDate) {
            if (fromDate == null)
                return this;
            Balance.this.setToDate(toDate);
            return this;
        }

        public Builder setPreviousBalance(Balance previousBalance){
            if (previousBalance == null)
                return this;
            if (!previousBalance.getAccount().get().equals(Balance.this.transaction.getAccount().get()))
                throw new IllegalArgumentException("The previous ballance account not equal this");
            this.previousBalance = Optional.of(previousBalance);
            return this;
        }

        public Balance build() throws IllegalArgumentException {
            Balance.this.balance = this.previousBalance.isPresent()
                                    ?this.previousBalance.get().getBallance()
                                    :0L;
            switch (Balance.this.transaction.getOperation()) {
                case INCREASE:
                    Balance.this.balance += Balance.this.transaction.getValue();
                    break;
                case REDUCE:
                    Balance.this.balance -= Balance.this.transaction.getValue();
                    break;
            }
            if (Balance.this.balance < 0L)
                throw new IllegalArgumentException("The ballance can not be less than zero");
            if(this.previousBalance.isPresent())
                this.previousBalance.get().setToDate(new Date(Balance.this.fromDate.getTime() - 1L));
            return Balance.this;
        }
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
