package co.com.empresa.certification.demoWeb.exceptions;

public class BackEndExceptions extends RuntimeException{
    public BackEndExceptions(){super();}
    public BackEndExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public BackEndExceptions(String message, Throwable cause){
        super(message, cause);
    }
    public BackEndExceptions(String message){
        super(message);
    }
    public BackEndExceptions(Throwable cause){
        super(cause);
    }
}
