package com.liam_armstrong.projectw1;

import com.liam_armstrong.projectw1.models.exceptions.CalEventNotFoundException;
import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import com.liam_armstrong.projectw1.models.Calendar;
import com.liam_armstrong.projectw1.models.Date;
import com.liam_armstrong.projectw1.models.Day;
import com.liam_armstrong.projectw1.models.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.liam_armstrong.projectw1.supers.CalEvent;

public class CalEventNotFoundException_tests {
    private Calendar testCal;
    private Date d;
    private Day dy;
    private CalEvent e;

    @BeforeEach
    public void init(){
        Calendar.resetInstance();
        testCal = Calendar.getInstance();
        try{d = new Date(31, 10, 2018);} catch (InvalidDateException e) { e.printStackTrace(); }
        dy = new Day();
        e = new Event("title");

        testCal.addCalEvent(d, e);
        dy.addCalEvent(e);
    }

    @Test
    public void test_DayImplementation_findBooking_pass(){
        try{
            Assertions.assertEquals(e, dy.findCalEvent("title"));
        } catch (CalEventNotFoundException e){
            Assertions.fail("Caught an unexpected exception: " + e);
        }
    }

    @Test
    public void test_DayImplementation_findBooking_fail(){
        boolean fail = true;
        try {
            dy.findCalEvent("nottitle");
        } catch (CalEventNotFoundException e){
            fail = false;
        }
        Assertions.assertFalse(fail);
    }

    @Test
    public void test_DayImplementation_removeBooking_pass(){
        try{
            Assertions.assertEquals(e, dy.findCalEvent("title"));
        } catch (CalEventNotFoundException e){
            Assertions.fail("Caught an unexpected exception: " + e);
        }
    }

    @Test
    public void test_DayImplementation_removeBooking_fail(){
        boolean fail = true;
        try {
            dy.findCalEvent("nottitle");
        } catch (CalEventNotFoundException e){
            fail = false;
        }
        Assertions.assertFalse(fail);
    }

    @Test
    public void test_CalImplementation_removeCalEvent_pass(){
        try{
            testCal.removeCalEvent(d, "title");
        } catch (CalEventNotFoundException e){
            Assertions.fail("Caught an unexpected exception: " + e);
        }
    }


    @Test
    public void test_CalImplementation_removeCalEvent_fail(){
        boolean fail = true;
        try {
            testCal.removeCalEvent(d,"nottitle");
        } catch (CalEventNotFoundException e){
            fail = false;
        }
        Assertions.assertFalse(fail);
    }
}
