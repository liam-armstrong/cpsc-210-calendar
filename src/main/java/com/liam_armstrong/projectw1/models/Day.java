package com.liam_armstrong.projectw1.models;

import com.liam_armstrong.projectw1.models.exceptions.CalEventNotFoundException;
import com.liam_armstrong.projectw1.models.exceptions.InvalidSaveFileException;
import com.liam_armstrong.projectw1.supers.CalEvent;
import com.liam_armstrong.projectw1.supers.Save;

import java.util.*;

public class Day extends Observable implements Save, Iterable<CalEvent> {
    private SortedSet<CalEvent> calEvents;

    public Day() {
        calEvents = new TreeSet<CalEvent>(new Comparator<CalEvent>() {
            @Override
            public int compare(CalEvent o1, CalEvent o2) {
                return o1.compare(o2);
            }
        });
        addObserver(StatisticObserver.getInstance());
    }

    @Override
    //EFFECTS prints list of all calEvents for the day
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (CalEvent i : calEvents) {
            sb.append("\t" + i.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    //EFFECTS prints data representing object entry with delimiters
    public String export() {
        StringBuffer sb = new StringBuffer();
        sb.append(calEvents.size() + ",");
        for (CalEvent i : calEvents) {
            sb.append(i.export() + ",");
        }
        return sb.toString();
    }

    //REQUIRES data to be formatted with proper delimiter
    //EFFECTS returns Calendar with all information that was present in String s
    public static Save parse(String s) throws InvalidSaveFileException {
        try {
            Day d = new Day();
            String[] data = s.split("\\,");
            for (int i = 1; i < data.length; i++) {
                d.addCalEvent((CalEvent)CalEvent.parse(data[i]), false);
            }
            if (d.bookingsSize() != Integer.parseInt(data[0])) {
                throw new InvalidSaveFileException("Day");
            }
            return d;
        } catch (InvalidSaveFileException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidSaveFileException("Day");
        }
    }

    //MODIFIES this
    //EFFECTS Adds given event to list of calEvents
    public void addCalEvent(CalEvent c) {
        calEvents.add(c);
        setChanged();
        notifyObservers(calEvents);
    }

    //MODIFIES this
    //EFFECTS Adds given event to list of calEvents
    public void addCalEvent(CalEvent c, boolean changed) {
        calEvents.add(c);
        if(changed){
            setChanged();
            notifyObservers(calEvents);
        }

    }

    //MODIFIES this
    //EFFECTS removes CalEvent with matching titles
    public void removeCalEvent(String title) throws CalEventNotFoundException{
        CalEvent c = findCalEvent(title);
        if(!c.equals(null)) {
            calEvents.remove(c);
            setChanged();
            notifyObservers(calEvents);
        }
    }

    //MODIFIES this
    //EFFECTS removes CalEvent
    public void removeCalEvent(CalEvent c) throws CalEventNotFoundException{
        calEvents.remove(c);
        setChanged();
        notifyObservers(calEvents);
    }

    //EFFECTS returns CalEvent with matching title
    public CalEvent findCalEvent(String title) throws CalEventNotFoundException{
        for (CalEvent i : calEvents) {
            if(i.getTitle().equals(title)){
                return i;
            }
        }
        throw new CalEventNotFoundException();
    }

    //EFFECTS return number of calEvents in Day
    public int bookingsSize() {
        return calEvents.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(calEvents);
    }

    @Override
    //EFFECTS returns boolean if Objects hold the same information
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Day)) {
            return false;
        }
        Day d = (Day) o;
        if (calEvents.size() != d.bookingsSize()){
            return false;
        }
        for (CalEvent ce: calEvents){
            if(!d.contains(ce)){
                return false;
            }
        }
        return true;
    }

    public boolean contains(CalEvent ce) {
        return calEvents.contains(ce);
    }

    @Override
    public Iterator<CalEvent> iterator() {
        return calEvents.iterator();
    }
}