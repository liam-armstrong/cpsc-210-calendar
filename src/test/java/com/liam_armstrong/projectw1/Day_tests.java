package com.liam_armstrong.projectw1;

import com.liam_armstrong.projectw1.models.Event;
import com.liam_armstrong.projectw1.models.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.liam_armstrong.projectw1.supers.CalEvent;


public class Day_tests {
    Day d;
    CalEvent e;

    @BeforeEach
    public void setup(){
        d = new Day();
        e = new Event("Title", 800, 900);
    }

    @Test
    public void testMakeBookingsOne(){
        Assertions.assertEquals(0, d.bookingsSize()); //bookings are empty
        d.addCalEvent(e); //Make a booking then add it to list
        Assertions.assertEquals(1, d.bookingsSize()); //bookings have one entry
    }

    @Test
    public void testMakeBookingsTwo(){
        Assertions.assertEquals(0, d.bookingsSize()); //bookings are empty
        d.addCalEvent(e); //Add it to list
        Assertions.assertEquals(1, d.bookingsSize()); //bookings have one entry
    }
}
