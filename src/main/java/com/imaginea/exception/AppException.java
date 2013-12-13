package com.imaginea.exception;

public class AppException extends Exception {

    private static final long serialVersionUID = -4531165848923653891L;

    private String msg;

    public AppException() {
    }

    public AppException(String msg) {
        this.msg = msg;
    }

    public AppException(String msg, Throwable e) {
        super(e);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return super.toString() + " : " + msg.toString();
    }

}
