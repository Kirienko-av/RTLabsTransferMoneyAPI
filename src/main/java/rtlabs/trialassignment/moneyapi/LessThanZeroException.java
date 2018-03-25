package rtlabs.trialassignment.moneyapi;

/**
 * Created by kirie on 25.03.2018.
 */
public class LessThanZeroException extends Exception{

    private Double value;
    public Double getValue(){return value;}
    public LessThanZeroException(String message, Double value){

        super(message);
        this.value=value;
    }
}
