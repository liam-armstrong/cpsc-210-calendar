package com.liam_armstrong.projectw1.models;

import com.liam_armstrong.projectw1.supers.CalEvent;

public class Event extends CalEvent {


    public Event(String title) {
        super(title);
    }

    public Event(String title, String description) {
        super(title, description);
    }

    public Event(String title, int start, int end) {
        super(title, start, end);
    }

    public Event(String title, String desc, int start, int end) {
        super(title, desc, start, end);
    }

    @Override
    //EFFECTS prints variables in presentable fashion
    public String toString(){
        if(endTime == 0){
            return "All day: " + title;
        }
        else{
            return String.format("%04d", startTime)+ " to " + String.format("%04d", endTime) + ": " + title;
        }
    }

    @Override
    //EFFECTS prints data representing object entry with delimiters
    public String export() {
        return "event$" + super.export();
    }
}
