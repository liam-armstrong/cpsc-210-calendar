package com.liam_armstrong.projectw1;

import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import com.liam_armstrong.projectw1.models.Event;
import com.liam_armstrong.projectw1.models.Calendar;
import com.liam_armstrong.projectw1.models.Date;
import com.liam_armstrong.projectw1.models.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.liam_armstrong.projectw1.supers.CalEvent;

public class Display_tests {
    Calendar c;
    Date d;
    Day dy;
    Event e;

    @BeforeEach
    public void init(){
        Calendar.resetInstance();
        c = Calendar.getInstance();
        try {
            d = new Date(10, 10, 1000);
        } catch (InvalidDateException e){
            e.printStackTrace();
        }
        dy = new Day();
        e = new Event("Title", 800, 900);
    }

    @Test
    public void testCalImplementationEmpty(){
        Assertions.assertEquals("", printInstance(c));
    }

    @Test
    public void testCalImplementationPopulated(){
        c.addCalEvent(d, e);
        Assertions.assertEquals("Events on 10-10-1000: \n\t0800 to 0900: Title\n", printInstance(c));
    }

    @Test
    public void testDayImplementationEmpty(){
        Assertions.assertEquals("", printInstance(dy));
    }

    @Test
    public void testDayImplementationPopulated(){
        dy.addCalEvent(e);//Add booking
        Assertions.assertEquals("\t0800 to 0900: Title\n", printInstance(dy));//Confirm details are printing
        CalEvent e2 = new Event("Title2", 1000, 1200);
        dy.addCalEvent(e2);//Add second booking
        Assertions.assertEquals("\t0800 to 0900: Title\n\t1000 to 1200: Title2\n", printInstance(dy));//Confirm strings are appending
    }

    @Test
    public void testDateImplementation(){
        Assertions.assertEquals("10-10-1000", printInstance(d));
    }

    @Test
    public void testCalEventImplementationAllDay(){
        e = new Event("Title", "Desc", 0, 0);
        Assertions.assertEquals("All day: Title", printInstance(e));
    }

    @Test
    public void testCalEventImplementationStartEnd(){
        e = new Event("Title", 800,900);
        Assertions.assertEquals("0800 to 0900: Title", printInstance(e));
    }

    private String printInstance(Object o){
        return o.toString();
    }
}
