package com.liam_armstrong.projectw1.supers;

import com.liam_armstrong.projectw1.models.Event;
import com.liam_armstrong.projectw1.models.Task;
import com.liam_armstrong.projectw1.models.exceptions.InvalidSaveFileException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Objects;

public abstract class CalEvent implements Save{
    protected String title;
    protected String description;
    protected int startTime;
    protected int endTime;

    public CalEvent(String title){
        this.title = title;
        this.description = null;
        this.endTime = 0;
        this.startTime = 0;
    }

    public CalEvent(String title, String description){
        this.title = title;
        this.description = description;
        this.endTime = 0;
        this.startTime = 0;
    }

    public CalEvent(String title, int start, int end){
        this.title = title;
        this.description = null;
        this.startTime = start;
        this.endTime = end;
    }

    public CalEvent(String title, String desc, int start, int end){
        this.title = title;
        this.description = desc;
        this.startTime = start;
        this.endTime = end;
    }

    //getters
    //EFFECTS returns variable
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    //setters
    //MODIFIES this
    //EFFECTS changes local variable
    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    //EFFECTS prints variables in presentable fashion
    public abstract String toString();

    @Override
    //EFFECTS prints data representing object entry with delimiters
    public String export(){
        if(description != null && endTime != 0){
            return "false$true$" + title + "$" + description + Integer.toString(startTime) + "$" + Integer.toString(endTime);
        }
        else if(description != null && endTime == 0){
            return "true$true$" + title + "$" + description;
        }
        else if(description == null && endTime != 0){
            return "false$false$" + title + "$" + Integer.toString(startTime) + "$" + Integer.toString(endTime);
        }
        else{
            return "true$false$" + title;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(title, description, startTime, endTime);
    }

    public int compare(CalEvent ce){
        if(this.startTime < ce.getStartTime()){
            return -1;
        } else if(this.startTime > ce.getStartTime()){
            return 1;
        } else if(this.endTime > ce.getEndTime()){
            return -1;
        } else if(this.endTime < ce.getEndTime()){
            return 1;
        } else if(this.equals(ce)){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    //EFFECTS returns boolean if Objects hold the same information
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof CalEvent)) {
            return false;
        } if(o == null){
            return false;
        }
        CalEvent e = (CalEvent) o;
        if((this.description == null && e.getDescription() == null)|| this.description.equals(e.getDescription())) {
            if ((this.title == null && e.getTitle() == null) || this.title.equals(e.getTitle())) {
                if (this.endTime == e.getEndTime() && this.startTime == e.getStartTime()) {
                    return true;
                }
            }
        }
        return false;
    }

    //EFFECTS returns Calendar with all information that was present in String s
    public static Save parse(String s) throws InvalidSaveFileException {
        try {
            String data[] = s.split("\\$");
            if(Boolean.parseBoolean(data[1])){
                //AllDay
                if(Boolean.parseBoolean(data[2])){
                    //Has desc
                    if (data[0].equalsIgnoreCase("event")) {
                        return new Event(data[3], data[4]);
                    } else {
                        return new Task(data[3], data[4]);
                    }
                } else {
                    //No desc
                    if (data[0].equalsIgnoreCase("event")) {
                        return new Event(data[3]);
                    } else {
                        return new Task(data[3]);
                    }
                }
            }else{
                //Has Start and end times
                if(Boolean.parseBoolean(data[2])){
                    //Has desc
                    if (data[0].equalsIgnoreCase("event")) {
                        return new Event(data[3], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                    } else {
                        return new Task(data[3], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                    }
                } else {
                    //No desc
                    if (data[0].equalsIgnoreCase("event")) {
                        return new Event(data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                    } else {
                        return new Task(data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                    }
                }
            }
        } catch (Exception e) {
            throw new InvalidSaveFileException("CalEvent");
        }
    }
}
