package exceptions;

public class UnsupportedEncodingRuntimeException extends RuntimeException {
    public UnsupportedEncodingRuntimeException(String msg){
        super(msg);
    }

    public UnsupportedEncodingRuntimeException(){
        super();
    }
}
