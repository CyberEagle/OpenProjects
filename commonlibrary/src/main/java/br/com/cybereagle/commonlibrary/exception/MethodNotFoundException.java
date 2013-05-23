package br.com.cybereagle.commonlibrary.exception;

public class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException(String message) {
        super(message);
    }
}
