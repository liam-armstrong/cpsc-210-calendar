package com.liam_armstrong.projectw1.ui;

import com.liam_armstrong.projectw1.models.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.io.*;
import java.util.Observable;
import java.util.Observer;

public class mainController implements Observer {
    private Calendar calendar;
    private dayController dayController;

    public AnchorPane header;
    public BorderPane body;

    public Button dayToggle;
    public Label selectedDate_Label;

    public mainController(){
        loadFromFile();
        Calendar.getInstance().setSelectedDate(Date.today());
        Calendar.getInstance().addObserver(this);
    }

    public void loadFromFile(){
        try {
            File file = new File("calendar.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            calendar = (Calendar)Calendar.parse(br.readLine());
        } catch (Exception e){
            calendar = Calendar.getInstance();
        }

        if(dayController != null){
            dayController.update();
        }
    }

    public void boot(){
        selectedDate_Label.setText(Calendar.getInstance().getSelectedDate().toString());
        toggleDayView();
    }

    private void toggleDayView() {
        AnchorPane dayView = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/day.fxml"));
        try {
            dayView = loader.load();
            dayController = loader.getController();
            dayController.boot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        body.getChildren().clear();
        body.setCenter(dayView);
    }

    public void toggleDayView(MouseEvent mouseEvent) {
        toggleDayView();
    }

    public void toggleAgendaView(MouseEvent mouseEvent) {
        AnchorPane agendaView = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/agenda.fxml"));
        try {
            agendaView = loader.load();
            agendaController agendaController = loader.getController();
            agendaController.boot();
        } catch (IOException e) {
            e.printStackTrace();
        }
        body.getChildren().clear();
        body.setCenter(agendaView);
    }

    public void toggleMonthView(MouseEvent mouseEvent) {
        AnchorPane monthView = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/month.fxml"));
        try {
            monthView = loader.load();
            monthController monthController = loader.getController();
            monthController.boot();
        } catch (IOException e) {
            e.printStackTrace();
        }
        body.getChildren().clear();
        body.setCenter(monthView);
    }

    public void saveToFile(MouseEvent mouseEvent) {
        String output = calendar.export();
        try {
            PrintWriter out = new PrintWriter("calendar.txt");
            out.println(output);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void popupStatistics(MouseEvent mouseEvent) {
        JOptionPane.showMessageDialog(null, StatisticObserver.getInstance().toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Date){
            selectedDate_Label.setText(Calendar.getInstance().getSelectedDate().toString());
        }
    }
}
