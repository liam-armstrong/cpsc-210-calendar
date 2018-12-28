package com.liam_armstrong.projectw1.models;

import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import com.liam_armstrong.projectw1.models.exceptions.InvalidSaveFileException;
import com.liam_armstrong.projectw1.supers.Save;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.util.Objects;

public class Date implements Save {
    private int day, month, year;

    public Date(int day, int month, int year) throws InvalidDateException{
        if(day > 31 || day < 0 || month > 12 || month < 0 || year > 9999 || year < 0){
            throw new InvalidDateException();
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static Date today() {
        LocalDate ld = LocalDate.now(TimeZone.getInstance().getTimezone());
        Date d = null;
        try {
            d = new Date(ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear());
        } catch (Exception e){
            e.printStackTrace();
        }
        return d;
    }

    @Override
    //EFFECTS prints data representing object entry with delimiters
    public String export(){
        return Integer.toString(day) + "Q" + Integer.toString(month) + "Q" + Integer.toString(year);
    }

    //REQUIRES data to be formatted with proper delimiter
    //EFFECTS returns Calendar with all information that was present in String s
    public static Save parse(String s) throws InvalidSaveFileException{
        try {
            String[] data = s.split("\\D");
            return new Date(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
        } catch (Exception e){
            throw new InvalidSaveFileException("Date");
        }
    }

    //EFFECTS returns int signifying which date that is further in the future
    public int compare(Date d){
        if(this.year == d.getYear()){
            if(this.month == d.getMonth()){
                if(this.day > d.getDay()){
                    return 1;
                }
                else if(this.day < d.getDay()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
            else if(this.month > d.getMonth()){
                return 1;
            }
            else{
                return -1;
            }
        }
        else if(this.year > d.getYear()){
            return 1;
        }
        else if(this.year < d.getYear()){
            return -1;
        }
        return 0;
    }

    //getters
    //EFFECTS returns value
    public int getDay() { return day; }
    public int getMonth() { return month; }
    public int getYear() { return year; }

    //setters
    //MODIFIES this
    //EFFECTS changes value
    public void setDay(int day) { this.day = day; }
    public void setMonth(int month) { this.month = month; }
    public void setYear(int year) { this.year = year; }

    //EFFECTS print format of date
    @Override
    public String toString(){
        return Integer.toString(this.day) + "-" + String.format("%02d", this.month) + "-" + String.format("%02d", this.year);
    }

    @Override
    public int hashCode(){
        return Objects.hash(day, month, year);
    }

    @Override
    //EFFECTS returns boolean if Objects hold the same information
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (!(o instanceof Date)) {
            return false;
        }
        Date d = (Date) o;
        return this.day == d.getDay() && this.month == d.getMonth() && this.year == d.getYear();
    }

    public LocalDate toLocalDate(){
        return LocalDate.of(year, month, day);
    }
}
