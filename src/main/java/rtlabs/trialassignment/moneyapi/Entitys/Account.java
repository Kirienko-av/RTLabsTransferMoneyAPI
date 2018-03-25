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
public class Account implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "balcance")
    private Double ballance; //replace to money type

    Account() {
        // default constructor
    }

    public Account(String name, Double ballance) throws LessThanZeroException {
        if (ballance < 0) throw new LessThanZeroException("The ballance can not be less than zero", ballance);
        this.name = name;
        this.ballance = ballance;
    }

    public Account(String name) throws LessThanZeroException {
        this(name, 0D);
    }


    public Double getBallance() {
        return ballance;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

        public void ballanceIncrease(Double value) throws LessThanZeroException {
        if (value < 0) throw new LessThanZeroException("The value can not be less than zero", value);
        ballance += value;

    }

    public void ballanceReduce(Double value) throws LessThanZeroException {
        if (value < 0) throw new LessThanZeroException("The value can not be less than zero", value);
        if ((ballance - value) < 0) throw new LessThanZeroException("The ballance can not be less than zero", ballance - value);
        ballance -= value;

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
}
