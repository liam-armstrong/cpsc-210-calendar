package com.liam_armstrong.projectw1;

import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import com.liam_armstrong.projectw1.models.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvalidDateException_tests {
    @Test
    public void ValidDateInput(){
        try{
            Date d = new Date(10, 05, 1999);
        } catch (InvalidDateException e) {
            Assertions.fail("Caught an unexpected exception: " + e);
        }
    }

    @Test
    public void InvalidDateInputs(){
        InvalidDateCreation(-3, 2, 2000);
        InvalidDateCreation(40, 2, 2000);
        InvalidDateCreation(20, -3, 2000);
        InvalidDateCreation(20, 53, 2000);
        InvalidDateCreation(20, 6, -1000);
        InvalidDateCreation(20, 6, 15000);
    }

    private void InvalidDateCreation(int day, int month, int year){
        boolean fail = true;
        try{
            Date d = new Date(day, month, year);
        } catch (InvalidDateException e) {
            fail = false;
            return;
        }
        Assertions.assertFalse(fail);
    }
}
