package com.liam_armstrong.projectw1;
import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import com.liam_armstrong.projectw1.models.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class Date_tests {
    Date d1;
    Date d2;
    Date d3;
    Date d4;
    Date d5;
    Date d6;

    @BeforeEach
    public void setup(){
        try {
            d1 = new Date(1, 1, 1000); //January 1st, 1000
            d2 = new Date(1, 1, 1000); //January 1st, 1000
            d3 = new Date(1, 1, 2000); //January 1st, 2000
            d4 = new Date(1, 5, 1000); //May 1st, 1000
            d5 = new Date(30, 1, 1000); //January 30th, 1000
        } catch (InvalidDateException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDateCompare(){
        Assertions.assertEquals(d1.compare(d1), 0); // Same day
        Assertions.assertEquals(d1.compare(d2), 0); // Same date, different Day

        Assertions.assertEquals(d1.compare(d3), -1); // More recent year
        Assertions.assertEquals(d3.compare(d1), 1); // More recent year inverse

        Assertions.assertEquals(d1.compare(d4), -1); // More recent month
        Assertions.assertEquals(d4.compare(d1), 1); // More recent month inverse

        Assertions.assertEquals(d1.compare(d5), -1); // More recent day
        Assertions.assertEquals(d5.compare(d1), 1); // More recent day inverse
    }
}
