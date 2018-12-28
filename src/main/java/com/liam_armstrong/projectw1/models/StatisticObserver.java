package com.liam_armstrong.projectw1.models;

import com.liam_armstrong.projectw1.supers.Save;
import com.liam_armstrong.projectw1.ui.mainController;

import java.util.Observable;
import java.util.Observer;

public class StatisticObserver implements Observer {
    private static StatisticObserver singleStatistic = null;
    private int daysAdded, calEventsAdded, calEventsAddedUnsaved, daysAddedUnsaved;

    private StatisticObserver(){
        calEventsAdded = 0;
        daysAdded = 0;
        calEventsAddedUnsaved = 0;
        daysAddedUnsaved = 0;
    }

    public static StatisticObserver getInstance(){
        if(singleStatistic == null){
            singleStatistic = new StatisticObserver();
        }
        return singleStatistic;
    }

    public static void resetInstance(){
        singleStatistic = null;
    }

    @Override
    public void update(Observable o, Object oj) {
        if(oj instanceof String){
            String arg = (String)oj;
            if(arg.equalsIgnoreCase("Saved")){
                calEventsAddedUnsaved = 0;
                daysAddedUnsaved = 0;
            }
        } else if(o instanceof Day) {
            calEventsAdded++;
            calEventsAddedUnsaved++;
        } else if(o instanceof Calendar) {
            daysAdded++;
            daysAddedUnsaved++;
        }
    }

    @Override
    public String toString(){
        return "Total days changed this session: " + Integer.toString(daysAdded) + "\n" + "Total calender entries changed this session: " + Integer.toString(calEventsAdded) + "\n" +
                "Total unsaved day changes this session: " + Integer.toString(daysAddedUnsaved) + "\n" + "Total unsaved calender entries changes this session: " + Integer.toString(calEventsAddedUnsaved);
    }
}
