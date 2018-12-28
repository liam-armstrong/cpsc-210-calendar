package com.liam_armstrong.projectw1;
import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import com.liam_armstrong.projectw1.models.Calendar;
import com.liam_armstrong.projectw1.models.Date;

import com.liam_armstrong.projectw1.models.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.liam_armstrong.projectw1.supers.CalEvent;

public class Calendar_tests {
    private Calendar testCal;
    private Date d;
    private CalEvent ce;

    @BeforeEach
    public void setup(){
        Calendar.resetInstance();
        testCal = Calendar.getInstance();
        try {
            d = new Date(30, 12, 1999);//Random date
        } catch (InvalidDateException e){
            e.printStackTrace();
        }
        ce = new Event("title", 800, 900);
    }

    @Test
    public void testAddEventInvalidDate(){
        Assertions.assertFalse(testCal.contains(d));//Check there is no Day at Date in Calendar
        testCal.addCalEvent(d, ce); //Add Day to Calendar and Event to Day
        Assertions.assertTrue(testCal.contains(d)); //Check Day object was added to Cal at Date
        Assertions.assertEquals(1, testCal.getDay(d).bookingsSize()); //Check an Event was added to Day's Events
    }

    @Test
    public void testAddEventValidDate(){
        Assertions.assertFalse(testCal.contains(d));//Check there is no Day at Date in Calendar
        testCal.addCalEvent(d, ce); //Add Day to Calendar and Event to Day
        Assertions.assertTrue(testCal.contains(d)); //Check Day object was added to Cal at Date
        Assertions.assertEquals(1, testCal.getDay(d).bookingsSize()); //Check an Event was added to Day's Events
    }

    @Test
    public void testAddCalEventDayAlreadyInList(){
        Assertions.assertFalse(testCal.contains(d)); //Check there is no Day at Date in Calendar
        testCal.addCalEvent(d, ce); //Add Day to Calendar and Event to Day
        Assertions.assertTrue(testCal.contains(d)); //Check Day object was added to Cal at Date
        Assertions.assertEquals(testCal.getDay(d).bookingsSize(), 1); //Check an Event was added to Day's Events

        testCal.addCalEvent(d, ce); //Add Day to Calendar and Event to Day
        Assertions.assertTrue(testCal.contains(d));//Check Day object still in Calendar
        Assertions.assertEquals(1, testCal.size());//Check Calendar still only has one Day in it

        Assertions.assertEquals(2, testCal.getDay(d).bookingsSize());//Check there is a second Event in Day
    }
}
