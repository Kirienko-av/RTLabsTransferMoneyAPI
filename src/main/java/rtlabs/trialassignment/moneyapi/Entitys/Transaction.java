package rtlabs.trialassignment.moneyapi.Entitys;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import rtlabs.trialassignment.moneyapi.LessThanZeroException;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kirie on 24.03.2018.
 */

@Entity
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "is_increase")
    private Boolean increase;

    @Column(name = "value")
    private Double value; //replace to money type

    @Column(name = "status_id")
    private Integer status; //create new

    @ManyToOne()
    @JoinColumn(name = "transaction_id")
    private Transaction parentTransaction;

    Transaction() {
        // default constructor
    }

    public Transaction(Account account, Double value, boolean increase, Transaction parentTransaction) {
        this.account = account;
        this.value = value;
        this.increase = increase;
        this.status = 1;
        this.parentTransaction = parentTransaction;
    }

    public Transaction(Account account, Double value, boolean isIncrease) {
        this(account, value, isIncrease, null);
    }

    public Transaction(Account account, Double value) {
        this(account, value, true);
    }

    public Transaction(Account account) {
        this(account, 0D);
    }


    public Long getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }


    public Double getValue() {
        return value;
    }

    public Transaction getParent() {
        return parentTransaction;
    }

    public Boolean isIncrease(){
        return increase;
    }



    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .append(getAccount().getId())
                .append(getParent().getId())
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
                .append(getAccount().getId(), other.getAccount().getId())
                .append(getParent().getId(), other.getParent().getId())
                .isEquals();
    }

    //@Override
    public void execute() throws LessThanZeroException {
            if ((parentTransaction != null && parentTransaction.getStatus() == 2) || parentTransaction == null) {
                if (increase) {
                    account.ballanceIncrease(value);
                } else {
                    account.ballanceReduce(value);
                }
                status = 2;
            } else if (parentTransaction != null && parentTransaction.getStatus() == 3) {
                status = 3;
            }

    }
}
