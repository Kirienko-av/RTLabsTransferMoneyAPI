package ru.trial_assigment.money_transfer.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

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

    public Account(){

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
                "', Name: '" + this.name  + "'";
    }
}
