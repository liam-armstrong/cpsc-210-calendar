package com.liam_armstrong.projectw1;

import com.liam_armstrong.projectw1.models.exceptions.InvalidSaveFileException;
import com.liam_armstrong.projectw1.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.liam_armstrong.projectw1.supers.CalEvent;

public class InvalidSaveFileException_tests {

    @Test
    public void CalendarValidInput(){
        try{
            Calendar.parse("1<<26Q11Q2018<<2,task$true$false$Task,event$false$false$Title$1200$1400,<<");
        } catch (InvalidSaveFileException e) {
            Assertions.fail("Caught an unexpected exception: " + e);
        }
    }

    @Test
    public void CalendarInValidInput(){
        boolean fail = true;
        try{
            Calendar.parse("5<<26Q11Q2018<<2,task$true$false$Task,event$false$false$Title$1200$1400,<<");
        } catch (InvalidSaveFileException e) {
            Assertions.assertEquals("Calendar", e.getOffender());
            fail = false;
            return;
        }
        Assertions.assertFalse(fail);
    }

    @Test
    public void DayValidInput(){
        try{
            Day.parse("2,task$true$false$Task,event$false$false$Title$1200$1400,");
        } catch (InvalidSaveFileException e) {
            Assertions.fail("Caught an unexpected exception: " + e);
        }
    }

    @Test
    public void DayInvalidInput(){
        boolean fail = true;
        try{
            Day.parse("5,task$true$false$Task,event$false$false$Title$1200$1400,");
        } catch (InvalidSaveFileException e) {
            Assertions.assertEquals("Day", e.getOffender());
            fail = false;
            return;
        }
        Assertions.assertFalse(fail);
    }

    @Test
    public void DateValidInput(){
        try{
            Date.parse("23Q10Q2018");
        } catch (InvalidSaveFileException e) {
            Assertions.fail("Caught an unexpected exception: " + e);
        }
    }

    @Test
    public void DateInvalidInput(){
        boolean fail = true;
        try{
            Date.parse("2310Q2018");
        } catch (InvalidSaveFileException e) {
            Assertions.assertEquals("Date", e.getOffender());
            fail = false;
            return;
        }
        Assertions.assertFalse(fail);
    }

    @Test
    public void CalEventValidInput(){
        try{
            CalEvent.parse("task$true$false$Task");
        } catch (InvalidSaveFileException e) {
            Assertions.fail("Caught an unexpected exception: " + e);
        }
    }

    @Test
    public void CalEventInvalidInput(){
        boolean fail = true;
        try{
            CalEvent.parse("task$falsefalse$Finish CPSC210 GUI");
        } catch (InvalidSaveFileException e) {
            fail = false;
            return;
        }
        Assertions.assertFalse(fail);
    }
}
