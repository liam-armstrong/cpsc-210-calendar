package com.liam_armstrong.projectw1;

import com.liam_armstrong.projectw1.models.*;
import com.liam_armstrong.projectw1.models.exceptions.CalEventNotFoundException;
import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import com.liam_armstrong.projectw1.models.exceptions.InvalidSaveFileException;
import com.liam_armstrong.projectw1.supers.CalEvent;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.*;
import java.util.Scanner;


public class consoleUi {
    public Scanner in;
    public Calendar calendar;

    //EFFECT initializes variables and prints instructions
    public consoleUi() throws IOException, GeoIp2Exception {
        boot();
        in = new Scanner(System.in);
    }

    //MODIFIES Calendar, Date, Day, Event
    //EFFECTS builds objects based on String in file, or new calendar if file not found/invalid
    private void boot(){
        try {
            File file = new File("calendar.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            calendar = (Calendar)Calendar.parse(br.readLine());
        } catch (FileNotFoundException e) {
            System.out.println("No saved calender found, booting new Calendar");
            calendar = Calendar.getInstance();
        } catch (IOException e) {
            calendar = Calendar.getInstance();
            e.printStackTrace();
        } catch (InvalidSaveFileException e){
            calendar = Calendar.getInstance();
            System.out.println("Invalid save file, booting new Calendar");
        } finally {
            printInstruct();
        }
    }

    //EFFECTS prints top level instructions for UI
    private void printInstruct(){
        System.out.println("Type 'edit' to edit events in your calender");
        System.out.println("Type 'view' to see your whole calender");
        System.out.println("Type 'save' to save your calender to file");
        System.out.println("Type 'import' to import your calender from file");
        System.out.println("Type 'exit' to quit");
        System.out.print("input: ");
    }

    //EFFECTS reads users top level input and begins other processes
    private void menu(consoleUi i){
        switch (in.nextLine()) {
            case "edit":
                i.selectDay();
                System.out.print(calendar.toString());
                i.printInstruct();
                break;
            case "view":
                System.out.print(calendar.toString());
                System.out.println(StatisticObserver.getInstance().toString());
                i.printInstruct();
                break;
            case "save":
                i.save();
                i.printInstruct();
                break;
            case "exit":
                i.save();
                System.out.println(StatisticObserver.getInstance().toString());
                System.exit(0);
                break;
            default:
                i.printInstruct();
                break;
        }
    }

    private void printDay(Date d){
        if(!calendar.contains(d) || calendar.getDay(d).bookingsSize() == 0){
            System.out.println("There are not any events on date " + d);
        } else {
            System.out.println("There are these events on date " + d + ": ");
            System.out.print(calendar.getDay(d).toString());
        }
    }

    //MODIFIES Calendar
    //EFFECTS Creates Date object given user's input and calls addEvent
    private void selectDay(){
        System.out.println("Which day would you like to select");
        System.out.println("Enter date in format dd mm yyyy");
        System.out.print("input: ");
        int day = in.nextInt();
        int month = in.nextInt();
        int year = in.nextInt();
        in.nextLine();
        try {
            Date d = new Date(day, month, year);
            boolean entering = true;
            while(entering) {
                printDay(d);
                System.out.println("Would you like to add or remove events from " + d.toString());
                System.out.println("Enter 'add', 'remove' or 'exit': ");
                System.out.print("input: ");
                String input = in.nextLine();
                if (input.equalsIgnoreCase("add")) {
                    addEvent(d);
                } else if (input.equalsIgnoreCase("remove")) {
                    if (!calendar.contains(d) || calendar.getDay(d).bookingsSize() == 0) {
                        System.out.println("There are no events on this day to remove\n");
                    } else {
                        removeEvent(d);
                    }
                } else {
                    entering = false;
                }
            }
        } catch(InvalidDateException e){
            System.out.println("Invalid date entered\n");
            selectDay();
        }
    }

    private void removeEvent(Date d) {
        boolean removing = true;
        printDay(d);
        while (removing) {
            if(calendar.getDay(d).bookingsSize() == 0){
                System.out.println("There are no more events or tasks on this day");
                break;
            }
            System.out.println("Enter an event/task title to remove it or 'exit' to go back");
            System.out.print("input: ");
            String input = in.nextLine();
            if(input.equalsIgnoreCase("exit")){
                removing = false;
            } else {
                try {
                    calendar.removeCalEvent(d, input);
                    System.out.println("Event: " + input + " has been removed");
                } catch (CalEventNotFoundException e){
                    System.out.println("Cannot find an event with that title");
                }
            }
        }
    }

    //MODIFIES Day
    //EFFECTS Adds event to Day object at Date in Calendar
    private void addEvent(Date d){
        boolean enteringEvents = true;
        while(enteringEvents) {
            System.out.println("Enter 'task' or 'event' to add to this day");
            System.out.println("Or enter 'exit' to select a different day");
            System.out.print("input: ");
            String input = in.nextLine();
            if(input.equalsIgnoreCase("exit")){
                break;
            }
            int start, end;
            String title, desc;
            CalEvent ce = null;

            System.out.println("Enter a title: ");
            System.out.print("input: ");
            title = in.nextLine();
            System.out.println("When would you like the task to start and end?");
            System.out.println("Enter start and end in format hhhh hhhh (24h)");
            System.out.print("input: ");
            start = in.nextInt();
            end = in.nextInt();
            in.nextLine();
            switch (input) {
                case "task":
                    ce = new Task(title, start, end);
                    System.out.println("Task \'" + title + "\' created");
                    break;
                case "event":
                    ce = new Event(title, start, end);
                    System.out.println("Event \'" + title + "\' created");
                    break;
                case "exit":
                    enteringEvents = false;
                    break;
                default:
                    enteringEvents = false;
                    break;
            }

            calendar.addCalEvent(d, ce);
        }
    }

    //REQUIRES non-null object calendar
    //EFFECTS writes calender to file
    private void save(){
        String output = calendar.export();
        try {
            PrintWriter out = new PrintWriter("calendar.txt");
            out.println(output);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES this
    //EFFECT starts program, starts top level menu
    public static void main(String[] args) throws IOException, GeoIp2Exception {
        consoleUi consoleUi = new consoleUi();
        while(true) {
            consoleUi.menu(consoleUi);
        }
    }
}
