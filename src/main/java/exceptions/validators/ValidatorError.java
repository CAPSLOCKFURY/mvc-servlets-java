package exceptions.validators;

public class ValidatorError extends RuntimeException {

    public ValidatorError(){

    }

    public ValidatorError(String msg){
        super(msg);
    }

}
