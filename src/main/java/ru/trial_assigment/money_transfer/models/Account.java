package ru.trial_assigment.money_transfer.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.Optional;

/**
 * Created by kirie on 24.03.2018.
 */

@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @Where(clause = "accounts.id = ballances.account_id")
    @WhereJoinTable(clause = "now() BETWEEN ballances.from_date AND ballances.to_date")
    private Balance balance;

    Account() {
        // default constructor
    }

    public Account(String name) throws IllegalArgumentException {
        this.setName(name);
    } 


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Optional<Balance> getBalance() {
        if (this.balance == null)
            return Optional.empty();
        return Optional.of(this.balance);
    }
    
    public void setName(String name) {
    	if (name == null) throw new IllegalArgumentException("The Name not be NULL");
        this.name = name;
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
                "', Name: '" + this.name  +
                "', Ballance: '(" + ((this.balance==null)?"NULL":this.balance.toString()) + ")'";
    }
}
