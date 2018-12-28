package com.liam_armstrong.projectw1.ui;

import com.liam_armstrong.projectw1.models.Calendar;
import com.liam_armstrong.projectw1.models.Date;
import com.liam_armstrong.projectw1.models.exceptions.InvalidDateException;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.time.LocalDate;

public class monthController {
    public TextField dayField;
    public TextField monthField;
    public TextField yearField;
    public GridPane monthGrid;
    public Label monthLabel;

    public void changeDate(MouseEvent mouseEvent) {
        try{
            int day = Integer.parseInt(dayField.getText());
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());
            Date d = new Date(day, month, year);
            Calendar.getInstance().setSelectedDate(d);
            update();
        } catch (InvalidDateException e) {
            JOptionPane.showMessageDialog(null, "Invalid Date entered");
        }
    }
    
    public void boot(){
        update();
    }

    public void update(){
        Date d = Calendar.getInstance().getSelectedDate();
        monthLabel.setText(Integer.toString(d.getMonth()) + "-" + Integer.toString(d.getYear()));
        LocalDate ld = LocalDate.of(d.getYear(), d.getMonth(), 1);
        int x = ld.getDayOfWeek().getValue() % 7;
        int y = 1;
        while(ld.getMonthValue() == d.getMonth()){
            Button button = new Button();
            int dayOfMonth = ld.getDayOfMonth();
            button.setText(Integer.toString(dayOfMonth));
            button.setId(Integer.toString(dayOfMonth));
            if(dayOfMonth == d.getDay()){
                button.setStyle("-fx-font-weight: bold");
            }
            button.setMinWidth(50);
            button.setOnAction((ActionEvent ae) -> this.changeSelectedDate(ae));
            monthGrid.add(button, x, y);
            monthGrid.setHalignment(button, HPos.CENTER);
            x = (x + 1) % 7;
            if(x == 0){
                y++;
            }
            ld = ld.plusDays(1);
        }
    }

    public void changeSelectedDate(ActionEvent ae){
        Date s = Calendar.getInstance().getSelectedDate();
        Date d = null;
        Button b = (Button)ae.getSource();
        try{
            d = new Date(Integer.parseInt(b.getText()), s.getMonth(), s.getYear());
        } catch (InvalidDateException e){
            e.printStackTrace();
        }
        Calendar.getInstance().setSelectedDate(d);
        update();
    }
}
