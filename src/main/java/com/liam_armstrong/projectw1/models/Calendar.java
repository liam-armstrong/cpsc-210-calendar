package com.liam_armstrong.projectw1.models;

import com.liam_armstrong.projectw1.models.exceptions.CalEventNotFoundException;
import com.liam_armstrong.projectw1.models.exceptions.InvalidSaveFileException;
import com.liam_armstrong.projectw1.supers.CalEvent;
import com.liam_armstrong.projectw1.supers.Save;

import java.io.*;
import java.util.*;

public class Calendar extends Observable implements Save, Iterable<Map.Entry<Date, Day>> {
    private Date selectedDate;
    private SortedMap<Date, Day> dateDayMap;
    private static Calendar singleCalendar = null;

    //EFFECTS initializes TreeMap of Days
    private Calendar(){
        dateDayMap = new TreeMap<Date, Day>(new Comparator<Date>() {
            @Override
            public int compare(Date d1, Date d2) {
                return d1.compare(d2);
            }
        });
        addObserver(StatisticObserver.getInstance());
    }

    public static Calendar getInstance(){
        if(singleCalendar == null){
            singleCalendar = new Calendar();
        }
        return singleCalendar;
    }

    public static void resetInstance(){
        singleCalendar = null;
    }

    @Override
    //EFFECTS prints each item in calender in presentable manner
    public String toString(){
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<Date,Day> entry : dateDayMap.entrySet()){
            Date key = entry.getKey();
            Day value = entry.getValue();
            sb.append("Events on " + key.toString() + ": \n");
            sb.append(value.toString());
        }
        return sb.toString();
    }

    @Override
    //EFFECTS prints data representing object entry with delimiters
    public String export() {
        StringBuffer sb = new StringBuffer();
        sb.append(dateDayMap.size() + "<<");
        for(Map.Entry<Date,Day> entry : dateDayMap.entrySet()){
            Date key = entry.getKey();
            Day value = entry.getValue();
            sb.append(key.export() + "<<" + value.export() + "<<");
        }
        setChanged();
        notifyObservers("Saved");
        return sb.toString();
    }

    //EFFECTS returns Calendar with all information that was present in String s, or empty Calendar if string was empty
    public static Save parse(String s) throws InvalidSaveFileException{
        Calendar calendar = new Calendar();
        try {
            String[] data = s.split("\\<\\<");
            for (int i = 1; i < data.length; i = i + 2) {
                calendar.put((Date)Date.parse(data[i]), (Day)Day.parse(data[i + 1]));
            }
            if(calendar.size() != Integer.parseInt(data[0])){
                throw new InvalidSaveFileException("Calendar");
            }
            singleCalendar = calendar;
            return calendar;
        }
        catch (InvalidSaveFileException e) {
            throw e;
        }
        catch (Exception e) {
            throw new InvalidSaveFileException("Calendar");
        }
    }

    //MODIFIES Day
    //EFFECT adds event at start and end time to a day object
    public void addCalEvent(Date d, CalEvent ce){
        makeDay(d);
        dateDayMap.get(d).addCalEvent(ce);
    }

    public void removeCalEvent(Date d, String title) throws CalEventNotFoundException{
        dateDayMap.get(d).removeCalEvent(title);
    }

    public void removeCalEvent(CalEvent ce, Date d) {
        try {
            dateDayMap.get(d).removeCalEvent(ce);
        } catch (CalEventNotFoundException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES this
    //EFFECTS create Day object with day/month/year if it doesn't already exist
    private void makeDay(Date d){
        if(!dateDayMap.containsKey(d)){
            dateDayMap.put(d, new Day());
            setChanged();
            notifyObservers(dateDayMap);
        }
    }

    //EFFECTS returns Day object at Date
    public Day getDay(Date d){
        return dateDayMap.get(d);
    }

    public Date getSelectedDate() { return selectedDate; }

    public void setSelectedDate(Date d){
        this.selectedDate = d;
        setChanged();
        notifyObservers(d);
    }

    @Override
    //EFFECTS
    public int hashCode(){
        return Objects.hash(dateDayMap);
    }

    @Override
    //EFFECTS returns boolean if Objects hold the same information
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (!(o instanceof Calendar)) {
            return false;
        }
        Calendar cal = (Calendar) o;
        if(this.dateDayMap.size() != cal.size()){
            return false;
        }
        else{
            for(Map.Entry<Date,Day> entry : this.dateDayMap.entrySet()){
                Date key = entry.getKey();
                Day value = entry.getValue();
                if(!cal.getDay(key).equals(value)){
                    return false;
                }
            }
            return true;
        }
    }

    //TEST METHODS
    //EFFECTS true if there is a Day object at Date d in Cal
    public boolean contains(Date d){
        return dateDayMap.containsKey(d);
    }

    //REQUIRES no Day object at Date in Calendar, will overwrite
    //MODIFIES this
    //EFFECTS, places Day object at Date in Calendar
    public void put(Date d, Day dy){
        dateDayMap.put(d, dy);
    }

    //EFFECTS integer size of calender set
    public int size(){
        return dateDayMap.size();
    }

    @Override
    public Iterator<Map.Entry<Date, Day>> iterator() {
        return dateDayMap.entrySet().iterator();
    }
}