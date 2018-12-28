package com.liam_armstrong.projectw1.ui;

import com.liam_armstrong.projectw1.models.Calendar;
import com.liam_armstrong.projectw1.models.Date;
import com.liam_armstrong.projectw1.models.Event;
import com.liam_armstrong.projectw1.models.Task;
import com.liam_armstrong.projectw1.supers.CalEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import javax.sound.sampled.Line;
import javax.swing.*;

public class dayController {
    public CheckBox CalEventForm_allDay;
    public ChoiceBox CalEventForm_TypeSelection;
    public TextArea CalEventForm_Desc;
    public TextField CalEventForm_Title;
    public TextField CalEventForm_Start;
    public TextField CalEventForm_End;
    public Button CalEventForm_Submit;

    public LineChart scheduleChart;
    public NumberAxis xAxis;

    public ListView Info_ListView;
    public Text Info_Title;
    public Label Info_Desc;
    public Group Info_Group;
    public Button Info_Remove;
    private ObservableList CalEvents = FXCollections.observableArrayList();

    public void createCalEvent(MouseEvent mouseEvent) {
        CalEvent ce = null;
        int startTime = 0;
        int endTime = 0;
        try{
            startTime = Integer.parseInt(CalEventForm_Start.getText());
            endTime = Integer.parseInt(CalEventForm_End.getText());
        } catch (NumberFormatException e) {}

        try {
            if (CalEventForm_TypeSelection.getSelectionModel().isSelected(0)) {
                ce = new Event(CalEventForm_Title.getText(), CalEventForm_Desc.getText(),
                        startTime, endTime);
            } else if (CalEventForm_TypeSelection.getSelectionModel().isSelected(1)) {
                ce = new Task(CalEventForm_Title.getText(), CalEventForm_Desc.getText(),
                        startTime, endTime);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Exception caught: " + e);
        }
        Calendar.getInstance().addCalEvent(Calendar.getInstance().getSelectedDate(), ce);
        update();

        CalEventForm_allDay.setSelected(false);
        CalEventForm_Title.setText("");
        CalEventForm_Desc.setText("");
        CalEventForm_End.setText("");
        CalEventForm_Start.setText("");
        CalEventForm_End.setDisable(false);
        CalEventForm_Start.setDisable(false);
    }

    public void boot(){
        Info_ListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Info_ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CalEvent>() {
            @Override
            public void changed(ObservableValue<? extends CalEvent> observable, CalEvent oldValue, CalEvent newValue) {
                if(newValue != null) {
                    Info_Group.setVisible(true);
                    Info_Title.setText(newValue.getTitle());
                    if (newValue.getDescription() != null) {
                        Info_Desc.setText(newValue.getDescription());
                    } else {
                        Info_Desc.setText("No Description");
                    }
                } else {
                    Info_Group.setVisible(false);
                }
            }
        });
        scheduleChart.getYAxis().setTickLabelsVisible(false);
        scheduleChart.getYAxis().setOpacity(0);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(2400);
        xAxis.setTickUnit(100);
        xAxis.setMinorTickVisible(false);
        xAxis.setTickLabelFormatter(new StringConverter<Number>(){
            @Override
            public String toString(Number object) {
                String value = String.format("%04d", object.intValue());
                return value.substring(0, 2) + ":" + value.substring(2, 4);
            }
            @Override
            public Number fromString(String string) {
                return 0;
            }
        });
        update();
    }

    public void update(){
        Info_ListView.getSelectionModel().clearSelection();
        Info_ListView.getItems().clear();
        CalEvents.clear();
        scheduleChart.getData().clear();
        int i = 0;
        try {
            i = Calendar.getInstance().getDay(Calendar.getInstance().getSelectedDate()).bookingsSize();
            for (CalEvent ce : Calendar.getInstance().getDay(Calendar.getInstance().getSelectedDate())) {
                CalEvents.add(ce);
                XYChart.Series series = new XYChart.Series();
                int start = ce.getStartTime();
                int end = ce.getEndTime();
                if (end != 0) {
                    series.getData().add(new XYChart.Data(start, i));
                    series.getData().add(new XYChart.Data(end, i));
                } else {
                    series.getData().add(new XYChart.Data(0, i));
                    series.getData().add(new XYChart.Data(2400, i));
                }
                scheduleChart.getData().add(series);
                i--;
            }
        } catch (NullPointerException e){}
        Info_ListView.setItems(CalEvents);
    }

    public void disableTimeInput(ActionEvent actionEvent) {
        CalEventForm_End.setDisable(!CalEventForm_End.isDisabled());
        CalEventForm_Start.setDisable(!CalEventForm_Start.isDisabled());
    }

    public void removeCalEvent(ActionEvent actionEvent) {
        CalEvent selected = (CalEvent) Info_ListView.getSelectionModel().getSelectedItem();
        Calendar.getInstance().removeCalEvent(selected, Calendar.getInstance().getSelectedDate());
        update();
    }
}
