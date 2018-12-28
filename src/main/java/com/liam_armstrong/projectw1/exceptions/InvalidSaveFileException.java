package com.liam_armstrong.projectw1.models.exceptions;

public class InvalidSaveFileException extends Exception{
    private static final long serialVersionUID = 1L;
    private String offender;

    public InvalidSaveFileException(String offender) {
        super();
        this.offender = offender;
    }

    public String getOffender(){
        return offender;
    }
}