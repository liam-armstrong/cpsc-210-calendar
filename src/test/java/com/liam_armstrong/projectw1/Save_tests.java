package com.liam_armstrong.projectw1;

import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import com.liam_armstrong.projectw1.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.liam_armstrong.projectw1.supers.CalEvent;
import com.liam_armstrong.projectw1.supers.Save;


public class Save_tests {
    Calendar c;
    Date d;
    Day dy;
    CalEvent e;
    CalEvent et;

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
        e = new Event("title", 800, 900);
        et = new Task("task", 1000, 1200);
    }

    @Test
    public final void EventImplementationTest(){
        String export = export(e);
        Assertions.assertEquals("event$false$false$title$800$900", export);
        try {
            Assertions.assertTrue(e.equals(CalEvent.parse(export)));
        } catch (Exception e){
            Assertions.fail("Caught exception: " + e);
        }

    }

    @Test
    public final void TaskImplementationTest(){
        String export = export(et);
        Assertions.assertEquals("task$false$false$task$1000$1200", export);
        try {
            Assertions.assertTrue(et.equals(CalEvent.parse(export)));
        } catch (Exception e){
            Assertions.fail("Caught exception: " + e);
        }
    }

    @Test
    public final void DayImplementationTest(){
        dy.addCalEvent(e);
        String export = export(dy);
        Assertions.assertEquals("1,event$false$false$title$800$900,", export);
        try {
            Assertions.assertTrue(dy.equals(Day.parse(export)));
        } catch (Exception e){
            Assertions.fail("Caught exception: " + e);
        }
    }

    @Test
    public final void DateImplementationTest(){
        String export = export(d);
        Assertions.assertEquals("10Q10Q1000", export);
        try {
            Assertions.assertTrue(d.equals(Date.parse(export)));
            Assertions.assertEquals(0, d.compare((Date)Date.parse(export)));
        } catch (Exception e){
            Assertions.fail("Caught exception: " + e);
        }

    }

    @Test
    public final void CalendarImplementationTest(){
        dy.addCalEvent(e);
        c.put(d,dy);
        String export = export(c);
        Assertions.assertEquals("1<<10Q10Q1000<<1,event$false$false$title$800$900,<<", export);
        try {
            Assertions.assertTrue(c.equals(Calendar.parse(export)));
        } catch (Exception e){
            Assertions.fail("Caught exception: " + e);
        }
    }

    private String export(Save s){
        return s.export();
    }

    private String export(CalEvent e){
        return e.export();
    }
}