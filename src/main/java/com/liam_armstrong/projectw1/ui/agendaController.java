package com.liam_armstrong.projectw1.ui;

import com.liam_armstrong.projectw1.models.*;
import com.liam_armstrong.projectw1.supers.CalEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;

import java.util.Map;

public class agendaController {
    public ListView ListView;
    public Text Title;
    public Label Desc;
    public CheckBox Event_Check;
    public CheckBox Tasks_Check;
    public Group Group;
    private Date today;
    private boolean events, tasks;
    private ObservableList<CalEvent> calEvents = FXCollections.observableArrayList();

    public agendaController(){
        today = Date.today();
    }

    public void boot(){
        ListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CalEvent>() {
            @Override
            public void changed(ObservableValue<? extends CalEvent> observable, CalEvent oldValue, CalEvent newValue) {
                if(newValue != null) {
                    Group.setVisible(true);
                    Title.setText(newValue.getTitle());
                    if (newValue.getDescription() != null) {
                        Desc.setText(newValue.getDescription());
                    } else {
                        Desc.setText("No Description");
                    }
                } else {
                    Group.setVisible(false);
                }
            }
        });
        update();
    }

    private void update() {
        ListView.getSelectionModel().clearSelection();
        ListView.getItems().clear();
        calEvents.clear();
        for(Map.Entry<Date, Day> entry: Calendar.getInstance()){
            if(entry.getKey().compare(today) != -1){
                for(CalEvent ce: entry.getValue()){
                    calEvents.add(ce);
                }
            }
            if(calEvents.size() > 100){
                break;
            }
        }
        if(events == false){
            calEvents.removeIf((CalEvent ce) -> (ce instanceof Event));
        }
        if(tasks == false){
            calEvents.removeIf((CalEvent ce) -> (ce instanceof Task));
        }
        ListView.setItems(calEvents);
    }

    public void toggleCalEvents(ActionEvent actionEvent) {
        if(actionEvent.getSource() == Event_Check){
            events = Event_Check.isSelected();
        } else if(actionEvent.getSource() == Tasks_Check){
            tasks = Tasks_Check.isSelected();
        }
        update();
    }
}
